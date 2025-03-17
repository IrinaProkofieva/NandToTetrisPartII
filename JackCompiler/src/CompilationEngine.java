import enums.*;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class CompilationEngine {

    private static final List<Character> OPS =
            List.of('+', '-', '*', '/', '&', '|', '<', '>', '=');

    private static final List<Character> UNARY_OPS =
            List.of('-', '~');

    private final JackTokenizer tokenizer;
    private final VMWriter writer;
    private final SymbolTable symbolTable;
    private int counter;
    private String className;

    public CompilationEngine(
            File inputFile
    ) throws IOException {
        counter = 0;
        tokenizer = new JackTokenizer(inputFile);
        symbolTable = new SymbolTable();
        String filename = inputFile.getName();
        String outputFilepath = inputFile.toPath()
                .resolveSibling("output")
                .resolve(filename.substring(0, filename.length() - 5) + ".vm")
                .toString();
        writer = new VMWriter(outputFilepath);
    }

    private void assertEqualsAndAdvance(char value, char expected) throws IOException {
        if (tokenizer.tokenType() != TokenType.SYMBOL || value != expected) {
            throw new IllegalArgumentException("Invalid structure!");
        }
        next();
    }

    private void next() throws IOException {
        if (!tokenizer.hasMoreTokens()) {
            throw new IllegalStateException("Unexpected end of file!");
        }
        tokenizer.advance();
    }

    private String compileTypeAndAdvance() throws IOException {
        String type;
        if (tokenizer.tokenType() == TokenType.IDENTIFIER) {
            type = tokenizer.identifier();
        } else if (tokenizer.tokenType() == TokenType.KEYWORD
                && tokenizer.keyword().usedIn() == KeywordUsageGroup.TYPE) {
            type = tokenizer.keyword().name().toLowerCase();
        } else {
            throw new IllegalArgumentException("Invalid type structure!");
        }
        next();
        return type;
    }

    private void processInitIdentifierAndAdvance(String type, Keyword accessType) throws IOException {
        if (tokenizer.tokenType() != TokenType.IDENTIFIER) {
            throw new IllegalArgumentException("Invalid structure!");
        }
        SymbolKind kind;
        if (accessType == null) {
            kind = SymbolKind.ARG;
        } else {
            kind = switch (accessType) {
                case STATIC -> SymbolKind.STATIC;
                case FIELD -> SymbolKind.FIELD;
                case VAR -> SymbolKind.VAR;
                default -> throw new IllegalArgumentException("Invalid accessType: %s".formatted(accessType.name()));
            };
        }

        symbolTable.define(tokenizer.identifier(), type, kind);
        next();
    }

    private void compileVarSequence(Keyword accessType) throws IOException {
        String type = compileTypeAndAdvance();
        processInitIdentifierAndAdvance(type, accessType);

        while (tokenizer.tokenType() == TokenType.SYMBOL && tokenizer.symbol() == ',') {
            next();
            processInitIdentifierAndAdvance(type, accessType);
        }
    }

    // 'class' className '{' classVarDec* subroutineDec* '}'
    public void compileClass() throws IOException {
        try (writer){
            next();
            if (tokenizer.tokenType() != TokenType.KEYWORD || tokenizer.keyword() != Keyword.CLASS) {
                throw new IllegalArgumentException("Invalid structure!");
            }
            next();
            if (tokenizer.tokenType() != TokenType.IDENTIFIER) {
                throw new IllegalArgumentException("Invalid structure!");
            }
            this.className = tokenizer.identifier();
            next();
            assertEqualsAndAdvance(tokenizer.symbol(), '{');

            while (tokenizer.tokenType() == TokenType.KEYWORD &&
                    tokenizer.keyword().usedIn() == KeywordUsageGroup.CLASS_VAR_DEC) {
                compileClassVarDec();
            }

            while (tokenizer.tokenType() == TokenType.KEYWORD &&
                    tokenizer.keyword().usedIn() == KeywordUsageGroup.SUBROUTINE_DEC_1) {
                compileSubroutineDec();
            }

            if (tokenizer.symbol() != '}') {
                throw new IllegalArgumentException("Invalid structure!");
            }
        }
    }

    // ('static'|'field') type varName (',', varName)* ';
    public void compileClassVarDec() throws IOException {
        Keyword accessType = tokenizer.keyword();
        next();
        compileVarSequence(accessType);
        assertEqualsAndAdvance(tokenizer.symbol(), ';');
    }

    // ('constructor'|'function'|'method')('void'|type') subroutineName '(' parameterList ')' subroutineBody
    public void compileSubroutineDec() throws IOException {
        Keyword functionType = tokenizer.keyword();
        next();
        symbolTable.startSubroutine();
        if (functionType == Keyword.METHOD) {
            symbolTable.define("this", this.className, SymbolKind.ARG);
        }

        String returnType;
        if (tokenizer.tokenType() == TokenType.KEYWORD && tokenizer.keyword() == Keyword.VOID) {
            returnType = null;
            next();
        } else {
            returnType = compileTypeAndAdvance();
        }

        if (tokenizer.tokenType() != TokenType.IDENTIFIER) {
            throw new IllegalArgumentException("Invalid structure!");
        }
        String functionName = tokenizer.identifier();
        next();

        assertEqualsAndAdvance(tokenizer.symbol(), '(');
        compileParameterList();
        assertEqualsAndAdvance(tokenizer.symbol(), ')');

        compileSubroutineBody(functionType, "%s.%s".formatted(this.className, functionName), returnType == null);
    }

    // ((type varName) (',' type varName)*)?
    public void compileParameterList() throws IOException {
        if (tokenizer.tokenType() != TokenType.SYMBOL) {
            String type = compileTypeAndAdvance();
            processInitIdentifierAndAdvance(type, null);

            while (tokenizer.tokenType() == TokenType.SYMBOL && tokenizer.symbol() == ',') {
                next();
                type = compileTypeAndAdvance();
                processInitIdentifierAndAdvance(type, null);
            }
        }
    }

    // '{' varDec* statements '}'
    public void compileSubroutineBody(Keyword functionType, String functionName, boolean isVoid) throws IOException {
        assertEqualsAndAdvance(tokenizer.symbol(), '{');

        while (tokenizer.tokenType() == TokenType.KEYWORD && tokenizer.keyword() == Keyword.VAR) {
            compileVarDec();
        }
        writer.writeFunction(functionName, symbolTable.varCount(SymbolKind.VAR));

        if (functionType == Keyword.METHOD) {
            writer.writePush(Segment.ARGUMENT, 0);
            writer.writePop(Segment.POINTER, 0); // THIS = argument 0
        } else if (functionType == Keyword.CONSTRUCTOR) {
            writer.writePush(Segment.CONSTANT, symbolTable.varCount(SymbolKind.FIELD));
            writer.writeCall("Memory.alloc", 1);
            writer.writePop(Segment.POINTER, 0);
        }
        compileStatements(isVoid);

        assertEqualsAndAdvance(tokenizer.symbol(), '}');
    }

    // 'var' type varName (',' varName)*';'
    public void compileVarDec() throws IOException {
        Keyword varType = tokenizer.keyword();
        next();

        compileVarSequence(varType);

        assertEqualsAndAdvance(tokenizer.symbol(), ';');
    }

    public void compileStatements(boolean isVoid) throws IOException {
        while (tokenizer.tokenType() == TokenType.KEYWORD
                && tokenizer.keyword().usedIn() == KeywordUsageGroup.STATEMENT) {
            switch (tokenizer.keyword()) {
                case LET -> compileLet();
                case IF -> compileIf(isVoid);
                case WHILE -> compileWhile(isVoid);
                case DO -> compileDo();
                case RETURN -> compileReturn(isVoid);
            }
        }
    }

    public void compileLet() throws IOException {
        next();
        String varName = tokenizer.identifier();
        next();
        boolean isArr = false;

        if (tokenizer.tokenType() != TokenType.SYMBOL) {
            throw new IllegalStateException("Invalid letStatement structure!");
        }

        if (tokenizer.symbol() == '[') {
            next();
            isArr = true;
            writer.writePush(symbolTable.kindOf(varName).getSegment(), symbolTable.indexOf(varName));
            compileExpression(true);
            writer.writeArithmetics(Command.ADD);
            assertEqualsAndAdvance(tokenizer.symbol(), ']');
        }

        assertEqualsAndAdvance(tokenizer.symbol(), '=');
        compileExpression(true);

        if (isArr) {
            writer.writePop(Segment.TEMP, 0);
            writer.writePop(Segment.POINTER, 1);
            writer.writePush(Segment.TEMP, 0);
            writer.writePop(Segment.THAT, 0);
        } else {
            writer.writePop(symbolTable.kindOf(varName).getSegment(), symbolTable.indexOf(varName));
        }
        assertEqualsAndAdvance(tokenizer.symbol(), ';');
    }

    public void compileIf(boolean isVoid) throws IOException {
        int labelCounter = this.counter;
        this.counter++;
        next();
        assertEqualsAndAdvance(tokenizer.symbol(), '(');
        compileExpression(true);
        writer.writeArithmetics(Command.NOT);
        String skipIfLabel = "%s.%s.%d".formatted(this.className, "SKIP_IF_LABEL", labelCounter);
        writer.writeIf(skipIfLabel);
        assertEqualsAndAdvance(tokenizer.symbol(), ')');

        assertEqualsAndAdvance(tokenizer.symbol(), '{');
        compileStatements(isVoid);
        assertEqualsAndAdvance(tokenizer.symbol(), '}');

        String skipElseLabel = "%s.%s.%d".formatted(this.className, "SKIP_ELSE_LABEL", labelCounter);
        writer.writeGoto(skipElseLabel);
        writer.writeLabel(skipIfLabel);
        if (tokenizer.tokenType() == TokenType.KEYWORD && tokenizer.keyword() == Keyword.ELSE) {
            next();
            assertEqualsAndAdvance(tokenizer.symbol(), '{');
            compileStatements(isVoid);
            assertEqualsAndAdvance(tokenizer.symbol(), '}');
        }
        writer.writeLabel(skipElseLabel);
    }

    public void compileWhile(boolean isVoid) throws IOException {
        int whileCounter = this.counter;
        this.counter++;
        next();
        String whileLabel = "%s.%s.%d".formatted(this.className, "WHILE", whileCounter);
        String endWhileLabel = "%s.%s.%d".formatted(this.className, "END_WHILE", whileCounter);
        assertEqualsAndAdvance(tokenizer.symbol(), '(');
        writer.writeLabel(whileLabel);
        compileExpression(true);
        writer.writeArithmetics(Command.NOT);
        writer.writeIf(endWhileLabel);
        assertEqualsAndAdvance(tokenizer.symbol(), ')');

        assertEqualsAndAdvance(tokenizer.symbol(), '{');
        compileStatements(isVoid);
        writer.writeGoto(whileLabel);
        assertEqualsAndAdvance(tokenizer.symbol(), '}');
        writer.writeLabel(endWhileLabel);
    }

    public void compileDo() throws IOException {
        next();
        compileSubroutineCall();
        assertEqualsAndAdvance(tokenizer.symbol(), ';');
    }

    public void compileReturn(boolean isVoid) throws IOException {
        next();

        if (tokenizer.tokenType() != TokenType.SYMBOL) {
            compileExpression(true);
        }

        if (isVoid) {
            writer.writePush(Segment.CONSTANT, 0);
        }

        writer.writeReturn();
        assertEqualsAndAdvance(tokenizer.symbol(), ';');
    }

    public void compileExpression(boolean shouldReturnAnything) throws IOException {
        compileTerm(shouldReturnAnything);

        while (tokenizer.tokenType() == TokenType.SYMBOL && OPS.contains(tokenizer.symbol())) {
            char op = tokenizer.symbol();
            next();
            compileTerm(true);
            switch (op) {
                case '+' -> writer.writeArithmetics(Command.ADD);
                case '-' -> writer.writeArithmetics(Command.SUB);
                case '*' -> writer.writeCall("Math.multiply", 2);
                case '/' -> writer.writeCall("Math.divide", 2);
                case '&' -> writer.writeArithmetics(Command.AND);
                case '|' -> writer.writeArithmetics(Command.OR);
                case '<' -> writer.writeArithmetics(Command.LT);
                case '>' -> writer.writeArithmetics(Command.GT);
                case '=' -> writer.writeArithmetics(Command.EQ);
            }
        }
    }

    public void compileTerm(boolean shouldReturnAnything) throws IOException {
        switch (tokenizer.tokenType()) {
            case INT_CONST -> {
                writer.writePush(Segment.CONSTANT, tokenizer.intVal());
                next();
            }
            case STRING_CONST -> {
                String str = tokenizer.stringVal();
                int[] chars = str.chars().toArray();
                writer.writePush(Segment.CONSTANT, chars.length);
                writer.writeCall("String.new", 1);
                for (int aChar : chars) {
                    writer.writePush(Segment.CONSTANT, aChar);
                    writer.writeCall("String.appendChar", 2);
                }
                next();
            }
            case KEYWORD -> {
                if (tokenizer.keyword().usedIn() == KeywordUsageGroup.KEYWORD_CONSTANT) {
                    switch (tokenizer.keyword()) {
                        case TRUE -> {
                            writer.writePush(Segment.CONSTANT, 1);
                            writer.writeArithmetics(Command.NEG);
                        }
                        case FALSE, NULL -> writer.writePush(Segment.CONSTANT, 0);
                        case THIS -> writer.writePush(Segment.POINTER, 0);
                    }
                    next();
                } else {
                    throw new IllegalStateException("Invalid term structure!");
                }
            }
            case SYMBOL -> {
                if (UNARY_OPS.contains(tokenizer.symbol())) {
                    char op = tokenizer.symbol();
                    next();
                    compileTerm(true);
                    switch (op) {
                        case '-' -> writer.writeArithmetics(Command.NEG);
                        case '~' -> writer.writeArithmetics(Command.NOT);
                    }
                } else if (tokenizer.symbol() == '(') {
                    next();
                    compileExpression(shouldReturnAnything);
                    assertEqualsAndAdvance(tokenizer.symbol(), ')');
                } else {
                    throw new IllegalStateException("Invalid term structure!");
                }
            }
            case IDENTIFIER -> {
                String varName = tokenizer.identifier();
                next();

                if (tokenizer.tokenType() == TokenType.SYMBOL) {
                    if (tokenizer.symbol() == '(' || tokenizer.symbol() == '.') {
                        compileSubroutineCallWithoutName(varName, shouldReturnAnything);
                    } else if (tokenizer.symbol() == '[') {
                        next();
                        writer.writePush(symbolTable.kindOf(varName).getSegment(), symbolTable.indexOf(varName));
                        compileExpression(true);
                        writer.writeArithmetics(Command.ADD);
                        writer.writePop(Segment.POINTER, 1);
                        writer.writePush(Segment.THAT, 0);
                        assertEqualsAndAdvance(tokenizer.symbol(), ']');
                    } else {
                        SymbolKind kind = symbolTable.kindOf(varName);
                        if (kind != SymbolKind.NONE) {
                            writer.writePush(kind.getSegment(), symbolTable.indexOf(varName));
                        }
                    }
                }
            }
        }
    }

    public int compileExpressionList() throws IOException {
        int nArg = 0;
        if (tokenizer.tokenType() != TokenType.SYMBOL || tokenizer.symbol() != ')') {
            compileExpression(true);
            nArg++;

            while (tokenizer.tokenType() == TokenType.SYMBOL && tokenizer.symbol() == ',') {
                next();
                compileExpression(true);
                nArg++;
            }
        }
        return nArg;
    }

    private void compileSubroutineCall() throws IOException {
        String identifierName = tokenizer.identifier();
        next();
        compileSubroutineCallWithoutName(identifierName, false);
    }

    private void compileSubroutineCallWithoutName(String identifierName, boolean returnAnything) throws IOException {
        if (tokenizer.tokenType() != TokenType.SYMBOL) {
            throw new IllegalStateException("Invalid subroutineCall structure!");
        }
        if (tokenizer.symbol() == '(') {
            next();
            writer.writePush(Segment.POINTER, 0);
            int nArg = compileExpressionList() + 1;
            assertEqualsAndAdvance(tokenizer.symbol(), ')');
            writer.writeCall("%s.%s".formatted(this.className, identifierName), nArg);
        } else if (tokenizer.symbol() == '.') {
            next();
            if (tokenizer.tokenType() != TokenType.IDENTIFIER) {
                throw new IllegalArgumentException("Invalid structure!");
            }
            String subroutineName = tokenizer.identifier();
            SymbolKind kind = symbolTable.kindOf(identifierName);
            boolean isMethod = (kind != SymbolKind.NONE);
            if (isMethod) {
                writer.writePush(kind.getSegment(), symbolTable.indexOf(identifierName));
            }
            next();

            assertEqualsAndAdvance(tokenizer.symbol(), '(');
            int nArg = compileExpressionList();
            assertEqualsAndAdvance(tokenizer.symbol(), ')');

            if (isMethod) {
                nArg++;
                identifierName = symbolTable.typeOf(identifierName);
            }
            writer.writeCall("%s.%s".formatted(identifierName, subroutineName), nArg);
        }
        if (!returnAnything) {
            writer.writePop(Segment.TEMP, 0);
        }
    }
}
