// 访问者接口
public interface ASTVisitor<T> {
    T visit(BinOp node);

    T visit(Number node);

    T visit(Variable node);

    T visit(Assign node);

    T visit(If node);

    T visit(For node);

    T visit(Block node);
}
