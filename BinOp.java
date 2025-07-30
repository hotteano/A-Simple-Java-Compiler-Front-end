public class BinOp extends ASTNode {
    private final ASTNode left;
    private final Token op;
    private final ASTNode right;

    public BinOp(ASTNode left, Token op, ASTNode right) {
        this.left = left;
        this.op = op;
        this.right = right;
    }

    public ASTNode getLeft() { return left; }
    public Token getOp() { return op; }
    public ASTNode getRight() { return right; }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}