public class Assign extends ASTNode {
    private final Variable left;
    private final ASTNode right;

    public Assign(Variable left, ASTNode right) {
        this.left = left;
        this.right = right;
    }

    public Variable getLeft() { return left; }
    public ASTNode getRight() { return right; }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);//返回赋值结点的指针
    }
}
