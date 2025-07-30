// For循环
public class For extends ASTNode {
    private final Variable var;
    private final ASTNode start;
    private final ASTNode end;
    private final ASTNode step;
    private final ASTNode body;

    public For(Variable var, ASTNode start, ASTNode end, ASTNode step, ASTNode body) {
        this.var = var;
        this.start = start;
        this.end = end;
        this.step = step;
        this.body = body;
    }//最标准的初始化方法，没有任何的默认初始化构造器

    public Variable getVar() {
        return var;
    }

    public ASTNode getStart() {
        return start;
    }

    public ASTNode getEnd() {
        return end;
    }

    public ASTNode getStep() {
        return step;
    }

    public ASTNode getBody() {
        return body;
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
