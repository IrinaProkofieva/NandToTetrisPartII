public enum TokenType {
    KEYWORD("keyword"),
    SYMBOL("symbol"),
    IDENTIFIER("identifier"),
    INT_CONST("integerConstant"),
    STRING_CONST("stringConstant");

    private final String tag;

    TokenType(String tag) {
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }
}
