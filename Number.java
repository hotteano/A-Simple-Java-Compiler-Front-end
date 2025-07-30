// 数字
public class Number extends ASTNode {
    private final Token token;
    private final Object value;

    public Number(Token token) {
        this.token = token;
        this.value = token.getType() == TokenType.INTEGER
                ? Integer.parseInt(token.getValue())
                : Float.parseFloat(token.getValue());
    }

    public Token getToken() {
        return token;
    }

    public Object getValue() {
        return value;
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
