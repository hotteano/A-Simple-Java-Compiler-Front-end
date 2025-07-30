import java.util.List;

// 代码块
public class Block extends ASTNode {
    private final List<ASTNode> statements;

    public Block(List<ASTNode> statements) {
        this.statements = statements;
    }//一系列stat的组合

    public List<ASTNode> getStatements() {
        return statements;
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
