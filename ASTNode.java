// AST节点基类
public abstract class ASTNode {
    public abstract <T> T accept(ASTVisitor<T> visitor);
}


