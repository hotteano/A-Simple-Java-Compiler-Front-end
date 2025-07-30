// AST打印器
class ASTPrinter implements ASTVisitor<String> {
    private int indent = 0;

    private String indentStr() {
        return "  ".repeat(indent);
    }

    public String print(ASTNode node) {
        return node.accept(this);
    }//打印抽象语法树

    @Override
    public String visit(BinOp node) {
        return indentStr() + "BinOp(\n" +
                indentStr() + "  left: " + node.getLeft().accept(this) + ",\n" +
                indentStr() + "  op: " + node.getOp().getType() + ",\n" +
                indentStr() + "  right: " + node.getRight().accept(this) + "\n" +
                indentStr() + ")";
    }//打印二元运算符

    @Override
    public String visit(Number node) {
        return indentStr() + "Number(" + node.getValue() + ")";
    }//打印数字

    @Override
    public String visit(Variable node) {
        return indentStr() + "Variable(" + node.getName() + ")";
    }//打印变量

    @Override
    public String visit(Assign node) {
        return indentStr() + "Assign(\n" +
                indentStr() + "  left: " + node.getLeft().accept(this) + ",\n" +
                indentStr() + "  right: " + node.getRight().accept(this) + "\n" +
                indentStr() + ")";
    }//打印赋值

    @Override
    public String visit(If node) {
        indent++;
        String result = indentStr() + "If(\n" +
                indentStr() + "  condition: " + node.getCondition().accept(this) + ",\n" +
                indentStr() + "  then: " + node.getThenBlock().accept(this);
        if (node.getElseBlock() != null) {
            result += ",\n" + indentStr() + "  else: " + node.getElseBlock().accept(this);
        }
        result += "\n" + indentStr() + ")";
        indent--;
        return result;
    }//打印if

    @Override
    public String visit(For node) {
        indent++;
        String result = indentStr() + "For(\n" +
                indentStr() + "  var: " + node.getVar().accept(this) + ",\n" +
                indentStr() + "  start: " + node.getStart().accept(this) + ",\n" +
                indentStr() + "  end: " + node.getEnd().accept(this) + ",\n" +
                indentStr() + "  step: " + node.getStep().accept(this) + ",\n" +
                indentStr() + "  body: " + node.getBody().accept(this) + "\n" +
                indentStr() + ")";
        indent--;
        return result;
    }//打印for

    @Override
    public String visit(Block node) {
        indent++;
        StringBuilder sb = new StringBuilder(indentStr() + "Block([\n");
        for (ASTNode stmt : node.getStatements()) {
            sb.append(stmt.accept(this)).append(",\n");
        }
        sb.append(indentStr() + "])");
        indent--;
        return sb.toString();
    }//打印代码块
}
