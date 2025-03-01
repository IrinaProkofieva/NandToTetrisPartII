import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Objects;

public class CompilationEngine {

    private static final List<Character> OPS =
            List.of('+', '-', '*', '/', '&', '|', '<', '>', '=');

    private static final List<Character> UNARY_OPS =
            List.of('-', '~');

    private final JackTokenizer tokenizer;
    private final PrintWriter writer;
    private int indentsNumber;

    public CompilationEngine(
            File inputFile
    ) throws IOException {
        indentsNumber = 0;
        tokenizer = new JackTokenizer(inputFile);
        String filename = inputFile.getName();
        String outputFilepath = inputFile.toPath()
                .resolveSibling("output")
                .resolve(filename.substring(0, filename.length() - 5) + "XML.xml")
                .toString();
        writer = new PrintWriter(new FileWriter(outputFilepath));
    }

    private void printTag(String value, boolean isOpen) {
        if (!isOpen) {
            value = "/" + value;
            indentsNumber--;
            writer.println("\t".repeat(indentsNumber) + "<%s>".formatted(value));
        } else {
            writer.println("\t".repeat(indentsNumber) + "<%s>".formatted(value));
            indentsNumber++;
        }
    }

    private String charToString(char value) {
        if (value == '<') {
            return  "&lt;";
        } else if (value == '>') {
            return  "&gt;";
        } else if (value == '&') {
            return  "&amp;";
        } else {
            return String.valueOf(value);
        }
    }

    private void printTerminalWithoutNext(TokenType type, String value) {
        if (tokenizer.tokenType() != type) {
            throw new IllegalArgumentException("Invalid structure!");
        }
        writer.println("\t".repeat(indentsNumber) + "<%s> %s </%s>".formatted(type.getTag(), value, type.getTag()));
    }

    private void printTerminalWithoutNext(TokenType type, String value, String expected) {
        if (!Objects.equals(value, expected)) {
            throw new IllegalArgumentException("Invalid structure!");
        }
        printTerminalWithoutNext(type, value);
    }

    private void printTerminal(TokenType type, String value) throws IOException {
        printTerminalWithoutNext(type, value);
        next();
    }

    private void printTerminal(char value) throws IOException {
        printTerminalWithoutNext(TokenType.SYMBOL, charToString(value));
        next();
    }

    private void printTerminal(TokenType type, String value, String expected) throws IOException {
        if (!Objects.equals(value, expected)) {
            throw new IllegalArgumentException("Invalid structure!");
        }
        printTerminalWithoutNext(type, value);
        next();
    }

    private void printTerminal(char value, char expected) throws IOException {
        printTerminal(TokenType.SYMBOL, charToString(value), charToString(expected));
    }

    private void next() throws IOException {
        if (!tokenizer.hasMoreTokens()) {
            throw new IllegalStateException("Unexpected end of file!");
        }
        tokenizer.advance();
    }

    private void compileType() throws IOException {
        if (tokenizer.tokenType() == TokenType.IDENTIFIER) {
            printTerminal(TokenType.IDENTIFIER, tokenizer.identifier());
        } else if (tokenizer.tokenType() == TokenType.KEYWORD
                && tokenizer.keyword().usedIn() == KeywordUsageGroup.TYPE) {
            printTerminal(TokenType.KEYWORD, tokenizer.keyword().name().toLowerCase());
        } else {
            throw new IllegalArgumentException("Invalid type structure!");
        }
    }

    private void compileVarSequence() throws IOException {
        compileType();
        printTerminal(TokenType.IDENTIFIER, tokenizer.identifier());

        while (tokenizer.tokenType() == TokenType.SYMBOL && tokenizer.symbol() == ',') {
            printTerminal(TokenType.SYMBOL, String.valueOf(tokenizer.symbol()));
            printTerminal(TokenType.IDENTIFIER, tokenizer.identifier());
        }
    }

    // 'class' className '{' classVarDec* subroutineDec* '}'
    public void compileClass() throws IOException {
        try (writer){
            next();
            printTag("class", true);
            printTerminal(TokenType.KEYWORD, tokenizer.keyword().name().toLowerCase(),
                    Keyword.CLASS.name().toLowerCase());
            printTerminal(TokenType.IDENTIFIER, tokenizer.identifier());
            printTerminal(tokenizer.symbol(), '{');

            while (tokenizer.tokenType() == TokenType.KEYWORD &&
                    tokenizer.keyword().usedIn() == KeywordUsageGroup.CLASS_VAR_DEC) {
                compileClassVarDec();
            }

            while (tokenizer.tokenType() == TokenType.KEYWORD &&
                    tokenizer.keyword().usedIn() == KeywordUsageGroup.SUBROUTINE_DEC_1) {
                compileSubroutineDec();
            }

            printTerminalWithoutNext(TokenType.SYMBOL, charToString(tokenizer.symbol()), "}");
            printTag("class", false);
        }
    }

    // ('static'|'field') type varName (',', varName)* ';
    public void compileClassVarDec() throws IOException {
        printTag("classVarDec", true);
        printTerminal(TokenType.KEYWORD, tokenizer.keyword().name().toLowerCase());
        compileVarSequence();
        printTerminal(tokenizer.symbol(), ';');
        printTag("classVarDec", false);
    }

    // ('constructor'|'function'|'method')('void'|type') subroutineName '(' parameterList ')' subroutineBody
    public void compileSubroutineDec() throws IOException {
        printTag("subroutineDec", true);
        printTerminal(TokenType.KEYWORD, tokenizer.keyword().name().toLowerCase());

        if (tokenizer.tokenType() == TokenType.KEYWORD && tokenizer.keyword() == Keyword.VOID) {
            printTerminal(TokenType.KEYWORD, tokenizer.keyword().name().toLowerCase());
        } else {
            compileType();
        }

        printTerminal(TokenType.IDENTIFIER, tokenizer.identifier());
        printTerminal(tokenizer.symbol(), '(');
        compileParameterList();
        printTerminal(tokenizer.symbol(), ')');

        compileSubroutineBody();
        printTag("subroutineDec", false);
    }

    // ((type varName) (',' type varName)*)?
    public void compileParameterList() throws IOException {
        printTag("parameterList", true);
        if (tokenizer.tokenType() != TokenType.SYMBOL) {
            compileType();
            printTerminal(TokenType.IDENTIFIER, tokenizer.identifier());

            while (tokenizer.tokenType() == TokenType.SYMBOL && tokenizer.symbol() == ',') {
                printTerminal(TokenType.SYMBOL, String.valueOf(tokenizer.symbol()));
                compileType();
                printTerminal(TokenType.IDENTIFIER, tokenizer.identifier());
            }
        }
        printTag("parameterList", false);
    }

    // '{' varDec* statements '}'
    public void compileSubroutineBody() throws IOException {
        printTag("subroutineBody", true);
        printTerminal(tokenizer.symbol(), '{');

        while (tokenizer.tokenType() == TokenType.KEYWORD && tokenizer.keyword() == Keyword.VAR) {
            compileVarDec();
        }
        compileStatements();

        printTerminal(tokenizer.symbol(), '}');
        printTag("subroutineBody", false);
    }

    // 'var' type varName (',' varName)*';'
    public void compileVarDec() throws IOException {
        printTag("varDec", true);
        printTerminal(TokenType.KEYWORD, tokenizer.keyword().name().toLowerCase());

        compileVarSequence();

        printTerminal(tokenizer.symbol(), ';');
        printTag("varDec", false);
    }

    public void compileStatements() throws IOException {
        printTag("statements", true);
        while (tokenizer.tokenType() == TokenType.KEYWORD
                && tokenizer.keyword().usedIn() == KeywordUsageGroup.STATEMENT) {
            switch (tokenizer.keyword()) {
                case LET -> compileLet();
                case IF -> compileIf();
                case WHILE -> compileWhile();
                case DO -> compileDo();
                case RETURN -> compileReturn();
            }
        }
        printTag("statements", false);
    }

    public void compileLet() throws IOException {
        printTag("letStatement", true);
        printTerminal(TokenType.KEYWORD, tokenizer.keyword().name().toLowerCase());
        printTerminal(TokenType.IDENTIFIER, tokenizer.identifier());

        if (tokenizer.tokenType() != TokenType.SYMBOL) {
            throw new IllegalStateException("Invalid letStatement structure!");
        }

        if (tokenizer.symbol() == '[') {
            printTerminal(TokenType.SYMBOL, String.valueOf(tokenizer.symbol()));
            compileExpression();
            printTerminal(tokenizer.symbol(), ']');
        }

        printTerminal(tokenizer.symbol(), '=');
        compileExpression();
        printTerminal(tokenizer.symbol(), ';');
        printTag("letStatement", false);
    }

    public void compileIf() throws IOException {
        printTag("ifStatement", true);
        printTerminal(TokenType.KEYWORD, tokenizer.keyword().name().toLowerCase());
        printTerminal(tokenizer.symbol(), '(');
        compileExpression();
        printTerminal(tokenizer.symbol(), ')');

        printTerminal(tokenizer.symbol(), '{');
        compileStatements();
        printTerminal(tokenizer.symbol(), '}');

        if (tokenizer.tokenType() == TokenType.KEYWORD && tokenizer.keyword() == Keyword.ELSE) {
            printTerminal(TokenType.KEYWORD, tokenizer.keyword().name().toLowerCase());
            printTerminal(tokenizer.symbol(), '{');
            compileStatements();
            printTerminal(tokenizer.symbol(), '}');
        }
        printTag("ifStatement", false);
    }

    public void compileWhile() throws IOException {
        printTag("whileStatement", true);
        printTerminal(TokenType.KEYWORD, tokenizer.keyword().name().toLowerCase());
        printTerminal(tokenizer.symbol(), '(');
        compileExpression();
        printTerminal(tokenizer.symbol(), ')');

        printTerminal(tokenizer.symbol(), '{');
        compileStatements();
        printTerminal(tokenizer.symbol(), '}');
        printTag("whileStatement", false);
    }

    public void compileDo() throws IOException {
        printTag("doStatement", true);
        printTerminal(TokenType.KEYWORD, tokenizer.keyword().name().toLowerCase());
        compileSubroutineCall();
        printTerminal(tokenizer.symbol(), ';');
        printTag("doStatement", false);
    }

    public void compileReturn() throws IOException {
        printTag("returnStatement", true);
        printTerminal(TokenType.KEYWORD, tokenizer.keyword().name().toLowerCase());
        if (tokenizer.tokenType() != TokenType.SYMBOL) {
            compileExpression();
        }
        printTerminal(tokenizer.symbol(), ';');
        printTag("returnStatement", false);
    }

    public void compileExpression() throws IOException {
        printTag("expression", true);

        compileTerm();

        while (tokenizer.tokenType() == TokenType.SYMBOL && OPS.contains(tokenizer.symbol())) {
            printTerminal(tokenizer.symbol());
            compileTerm();
        }

        printTag("expression", false);
    }

    public void compileTerm() throws IOException {
        printTag("term", true);
        switch (tokenizer.tokenType()) {
            case INT_CONST -> printTerminal(TokenType.INT_CONST, String.valueOf(tokenizer.intVal()));
            case STRING_CONST -> printTerminal(TokenType.STRING_CONST, tokenizer.stringVal());
            case KEYWORD -> {
                if (tokenizer.keyword().usedIn() == KeywordUsageGroup.KEYWORD_CONSTANT) {
                    printTerminal(TokenType.KEYWORD, tokenizer.keyword().name().toLowerCase());
                } else {
                    throw new IllegalStateException("Invalid term structure!");
                }
            }
            case SYMBOL -> {
                if (UNARY_OPS.contains(tokenizer.symbol())) {
                    printTerminal(tokenizer.symbol());
                    compileTerm();
                } else if (tokenizer.symbol() == '(') {
                    printTerminal(tokenizer.symbol());
                    compileExpression();
                    printTerminal(tokenizer.symbol(), ')');
                } else {
                    throw new IllegalStateException("Invalid term structure!");
                }
            }
            case IDENTIFIER -> {
                String currentIdentifier = tokenizer.identifier();
                TokenType currentType = tokenizer.tokenType();
                printTerminal(currentType, currentIdentifier);
                if (tokenizer.tokenType() == TokenType.SYMBOL) {
                    if (tokenizer.symbol() == '(' || tokenizer.symbol() == '.') {
                        compileSubroutineCallWithoutName();
                    } else if (tokenizer.symbol() == '[') {
                        printTerminal(tokenizer.symbol());
                        compileExpression();
                        printTerminal(tokenizer.symbol(), ']');
                    }
                }
            }
        }

        printTag("term", false);
    }

    public void compileExpressionList() throws IOException {
        printTag("expressionList", true);

        if (tokenizer.tokenType() != TokenType.SYMBOL || tokenizer.symbol() != ')') {
            compileExpression();

            while (tokenizer.tokenType() == TokenType.SYMBOL && tokenizer.symbol() == ',') {
                printTerminal(TokenType.SYMBOL, String.valueOf(tokenizer.symbol()));
                compileExpression();
            }
        }
        printTag("expressionList", false);
    }

    private void compileSubroutineCall() throws IOException {
        printTerminal(TokenType.IDENTIFIER, tokenizer.identifier());
        compileSubroutineCallWithoutName();
    }

    private void compileSubroutineCallWithoutName() throws IOException {
        if (tokenizer.tokenType() != TokenType.SYMBOL) {
            throw new IllegalStateException("Invalid subroutineCall structure!");
        }
        if (tokenizer.symbol() == '(') {
            printTerminal(tokenizer.symbol());
            compileExpressionList();
            printTerminal(tokenizer.symbol(), ')');
        } else if (tokenizer.symbol() == '.') {
            printTerminal(tokenizer.symbol());
            printTerminal(TokenType.IDENTIFIER, tokenizer.identifier());
            printTerminal(tokenizer.symbol(), '(');
            compileExpressionList();
            printTerminal(tokenizer.symbol(), ')');
        }
    }
}
