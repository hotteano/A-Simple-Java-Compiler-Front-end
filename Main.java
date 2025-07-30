public class Main {
    public static void main(String[] args) {
        String input = "{" +
                "x = 10;" +
                "y = 20;" +
                "z = x + y * 2;" +
                "if (z > 30) {" +
                "   result = 1;" +
                "} else {" +
                "   result = 0;" +
                "}" +
                "for (i = 1 to 10, 2) {" +
                "   sum = sum + i;" +
                "}" +
                "}";

        Lexer lexer = new Lexer(input);
        Parser parser = new Parser(lexer);
        ASTNode ast = parser.parse();

        // 打印AST
        ASTPrinter printer = new ASTPrinter();
        System.out.println(printer.print(ast));
    }
}

