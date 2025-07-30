import java.util.ArrayList;
import java.util.List;

public class Lexer {
    private final String input; //输入
    private int position = 0; //读头
    private char currentChar; // 当前字符缓存

    public Lexer(String input) {
        this.input = input;
        this.currentChar = input.charAt(0);
    } // 构造一个词法分析器实例

    private void advance() {
        position++;//向后读一个
        if (position >= input.length()) {
            currentChar = '\0'; // 超过读长，当前字符置空
        } else {
            currentChar = input.charAt(position);
        }//否则读入
    }

    private void skipWhitespace() {
        while (currentChar != '\0' && Character.isWhitespace(currentChar)) {
            advance();
        }//跳过空格
    }

    private Token integer() {
        StringBuilder result = new StringBuilder();
        while (currentChar != '\0' && Character.isDigit(currentChar)) {
            result.append(currentChar); //读入当前数字整数部分，读到小数点或空格退出
            advance();
        }

        if (currentChar == '.') {
            result.append(currentChar);
            advance();
            while (currentChar != '\0' && Character.isDigit(currentChar)) {
                result.append(currentChar);
                advance();
            }
            return new Token(TokenType.FLOAT, result.toString());
        }//读入的是浮点数

        return new Token(TokenType.INTEGER, result.toString());//读入的是整数
    }

    private Token identifier() {
        StringBuilder result = new StringBuilder();
        while (currentChar != '\0' && Character.isLetterOrDigit(currentChar)) {
            result.append(currentChar);
            advance();
        }

        String value = result.toString();
        // 检查是否是关键字
        for (TokenType type : TokenType.values()) {
            if (type.getValue() != null && type.getValue().equals(value)) {
                return new Token(type, value);
            }
        }

        return new Token(TokenType.IDENTIFIER, value);
    }

    public Token getNextToken() {
        while (currentChar != '\0') {
            if (Character.isWhitespace(currentChar)) {
                skipWhitespace();//跳过空格
                continue;
            }

            if (Character.isDigit(currentChar)) {
                return integer();
            }

            if (Character.isLetter(currentChar)) {
                return identifier();
            }

            // 处理多字符操作符
            if (currentChar == '=') {
                advance();
                if (currentChar == '=') {
                    advance();
                    return new Token(TokenType.EQ, "==");
                }
                return new Token(TokenType.ASSIGN, "=");
            }

            if (currentChar == '!') {
                advance();
                if (currentChar == '=') {
                    advance();
                    return new Token(TokenType.NEQ, "!=");
                }
                throw new RuntimeException("Expected !=, got !" + currentChar);
            }

            if (currentChar == '<') {
                advance();
                if (currentChar == '=') {
                    advance();
                    return new Token(TokenType.LTE, "<=");
                }
                return new Token(TokenType.LT, "<");
            }

            if (currentChar == '>') {
                advance();
                if (currentChar == '=') {
                    advance();
                    return new Token(TokenType.GTE, ">=");
                }
                return new Token(TokenType.GT, ">");
            }

            // 单字符操作符
            switch (currentChar) {
                case '+':
                    advance();
                    return new Token(TokenType.PLUS, "+");
                case '-':
                    advance();
                    return new Token(TokenType.MINUS, "-");
                case '*':
                    advance();
                    return new Token(TokenType.MULTIPLY, "*");
                case '/':
                    advance();
                    return new Token(TokenType.DIVIDE, "/");
                case '(':
                    advance();
                    return new Token(TokenType.LPAREN, "(");
                case ')':
                    advance();
                    return new Token(TokenType.RPAREN, ")");
                case '{':
                    advance();
                    return new Token(TokenType.LBRACE, "{");
                case '}':
                    advance();
                    return new Token(TokenType.RBRACE, "}");
                case ';':
                    advance();
                    return new Token(TokenType.SEMICOLON, ";");
                case ',':
                    advance();
                    return new Token(TokenType.COMMA, ",");
                default:
                    throw new RuntimeException("Unexpected character: " + currentChar);
            }
        }

        return new Token(TokenType.EOF, null);
    }
}
