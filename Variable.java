// 变量
public class Variable extends ASTNode {
    private final Token token;

    public Variable(Token token) {
        this.token = token;
    }

    public Token getToken() {
        return token;
    }

    public String getName() {
        return token.getValue();
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
