// If语句
public class If extends ASTNode {
    private final ASTNode condition;
    private final ASTNode thenBlock;
    private final ASTNode elseBlock;

    public If(ASTNode condition, ASTNode thenBlock, ASTNode elseBlock) {
        this.condition = condition;
        this.thenBlock = thenBlock;
        this.elseBlock = elseBlock;
    }

    public ASTNode getCondition() {
        return condition;
    }

    public ASTNode getThenBlock() {
        return thenBlock;
    }

    public ASTNode getElseBlock() {
        return elseBlock;
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
