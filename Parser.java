import java.util.ArrayList;
import java.util.List;

public class Parser {
    private final Lexer lexer;
    private Token currentToken;

    public Parser(Lexer lexer) {
        this.lexer = lexer; //词法分析器
        this.currentToken = lexer.getNextToken(); // 获得第一个字符
    }

    private void eat(TokenType type) {
        if (currentToken.getType() == type) {
            currentToken = lexer.getNextToken(); //类型判定，若类型正确，解析下一个
        } else {
            throw new RuntimeException("Expected " + type + ", got " + currentToken.getType());
        }
    }

    private ASTNode factor() { //因子解析，用于辅助解析表达式的项
        Token token = currentToken;
        if (token.getType() == TokenType.INTEGER || token.getType() == TokenType.FLOAT) { //读入的是数字
            eat(token.getType());//eat暗含向后读取一个字符
            return new Number(token);
        } else if (token.getType() == TokenType.LPAREN) { //读入括号，递归解析内部表达式
            eat(TokenType.LPAREN);
            ASTNode node = expr();
            eat(TokenType.RPAREN);
            return node;
        } else if (token.getType() == TokenType.IDENTIFIER) { //标识符
            eat(TokenType.IDENTIFIER);
            return new Variable(token);
        }
        throw new RuntimeException("Unexpected token: " + token);
    } //

    private ASTNode term() {
        ASTNode node = factor(); //解析项的内部表达式

        while (currentToken.getType() == TokenType.MULTIPLY ||
                currentToken.getType() == TokenType.DIVIDE) {
            Token token = currentToken;
            if (token.getType() == TokenType.MULTIPLY) {
                eat(TokenType.MULTIPLY); //向后识别一个，在返回中factor方法中返回乘项
            } else if (token.getType() == TokenType.DIVIDE) {
                eat(TokenType.DIVIDE);
            }

            node = new BinOp(node, token, factor());
        }//只用于乘除的项，剩下的项放到expr中处理

        return node;
    }

    private ASTNode expr() {
        ASTNode node = term();//解析乘除项

        while (currentToken.getType() == TokenType.PLUS ||
                currentToken.getType() == TokenType.MINUS) {
            Token token = currentToken;
            if (token.getType() == TokenType.PLUS) {
                eat(TokenType.PLUS);
            } else if (token.getType() == TokenType.MINUS) {
                eat(TokenType.MINUS);
            }

            node = new BinOp(node, token, term());
        }//单纯解析加减项

        return node;
    }

    private ASTNode comparison() {
        ASTNode node = expr();//递归解析表达式，比较的优先级低于项的计算

        while (currentToken.getType() == TokenType.EQ ||
                currentToken.getType() == TokenType.NEQ ||
                currentToken.getType() == TokenType.LT ||
                currentToken.getType() == TokenType.GT ||
                currentToken.getType() == TokenType.LTE ||
                currentToken.getType() == TokenType.GTE) {
            Token token = currentToken;
            eat(token.getType());
            node = new BinOp(node, token, expr());
        }

        return node;
    }

    private ASTNode assignment() {
        if (currentToken.getType() == TokenType.IDENTIFIER) {
            Token varToken = currentToken;
            eat(TokenType.IDENTIFIER);

            // 只有看到 = 才认为是赋值
            if (currentToken.getType() == TokenType.ASSIGN) {
                eat(TokenType.ASSIGN);
                return new Assign(new Variable(varToken), expr());//将左侧的变量和右侧的表达式组成赋值
            }
            // 否则回退（可能是变量引用）
            return new Variable(varToken);
        }
        return comparison();
    }

    private ASTNode statement() {
        if (currentToken.getType() == TokenType.IF) {
            return ifStatement();
        } else if (currentToken.getType() == TokenType.FOR) {
            return forStatement();
        } else if (currentToken.getType() == TokenType.LBRACE) {
            return block();
        } else {
            return assignment();
        }
    }

    private ASTNode ifStatement() {
        eat(TokenType.IF);
        eat(TokenType.LPAREN);
        ASTNode condition = comparison();
        eat(TokenType.RPAREN);
        ASTNode thenBlock = statement();

        ASTNode elseBlock = null;
        if (currentToken.getType() == TokenType.ELSE) {
            eat(TokenType.ELSE);
            elseBlock = statement();
        }

        return new If(condition, thenBlock, elseBlock);
    }

    private ASTNode forStatement() {
        eat(TokenType.FOR);
        Token varToken = currentToken;
        eat(TokenType.LPAREN);
        eat(TokenType.IDENTIFIER);
        eat(TokenType.ASSIGN);
        ASTNode start = expr();
        eat(TokenType.TO);
        ASTNode end = expr();

        ASTNode step = new Number(new Token(TokenType.INTEGER, "1"));
        if (currentToken.getType() == TokenType.COMMA) {
            eat(TokenType.COMMA);
            step = expr();
        }
        eat(TokenType.RPAREN);

        ASTNode body = statement();//允许解析双重循环

        return new For(new Variable(varToken), start, end, step, body);
    }

    private ASTNode block() {
        eat(TokenType.LBRACE);
        List<ASTNode> statements = new ArrayList<>();

        while (currentToken.getType() != TokenType.RBRACE &&
                currentToken.getType() != TokenType.EOF) {
            statements.add(statement());//每一条语句加入解析列表
            if (currentToken.getType() == TokenType.SEMICOLON) {
                eat(TokenType.SEMICOLON);//读到分号，继续解析下一条语句
            }
        }

        eat(TokenType.RBRACE);//读到代码块结束
        return new Block(statements);//返回一个代码块指针
    }

    public ASTNode parse() {
        return block();
    }
    //对于复杂的函数功能、宏定义、头文件、继承、泛型等现代化编程功能，还需要更多的技术进行实现
}
