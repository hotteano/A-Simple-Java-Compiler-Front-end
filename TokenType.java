public enum TokenType {
    // 基本类型
    INTEGER,
    FLOAT,
    IDENTIFIER,

    // 运算符
    PLUS("+"),
    MINUS("-"),
    MULTIPLY("*"),
    DIVIDE("/"),
    ASSIGN("="),

    // 比较运算符
    EQ("=="),
    NEQ("!="),
    LT("<"),
    GT(">"),
    LTE("<="),
    GTE(">="),

    // 关键字
    IF("if"),
    ELSE("else"),
    FOR("for"),
    IN("in"),
    TO("to"),
    DO("do"),
    END("end"),

    // 分隔符
    LPAREN("("),
    RPAREN(")"),
    LBRACE("{"),
    RBRACE("}"),
    SEMICOLON(";"),
    COMMA(","),

    // 其他
    EOF;

    private final String value;

    TokenType() {
        this.value = null;
    }

    TokenType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}