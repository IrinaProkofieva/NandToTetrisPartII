public enum Keyword {
    CLASS(KeywordUsageGroup.CLASS),
    METHOD(KeywordUsageGroup.SUBROUTINE_DEC_1),
    FUNCTION(KeywordUsageGroup.SUBROUTINE_DEC_1),
    CONSTRUCTOR(KeywordUsageGroup.SUBROUTINE_DEC_1),
    INT(KeywordUsageGroup.TYPE),
    BOOLEAN(KeywordUsageGroup.TYPE),
    CHAR(KeywordUsageGroup.TYPE),
    VOID,
    VAR,
    STATIC(KeywordUsageGroup.CLASS_VAR_DEC),
    FIELD(KeywordUsageGroup.CLASS_VAR_DEC),
    LET(KeywordUsageGroup.STATEMENT),
    DO(KeywordUsageGroup.STATEMENT),
    IF(KeywordUsageGroup.STATEMENT),
    ELSE,
    WHILE(KeywordUsageGroup.STATEMENT),
    RETURN(KeywordUsageGroup.STATEMENT),
    TRUE(KeywordUsageGroup.KEYWORD_CONSTANT),
    FALSE(KeywordUsageGroup.KEYWORD_CONSTANT),
    NULL(KeywordUsageGroup.KEYWORD_CONSTANT),
    THIS(KeywordUsageGroup.KEYWORD_CONSTANT);

    private final KeywordUsageGroup usedIn;

    Keyword() {
        this.usedIn = null;
    }

    Keyword(KeywordUsageGroup usedIn) {
        this.usedIn = usedIn;
    }

    public KeywordUsageGroup usedIn() {
        return usedIn;
    }
}
