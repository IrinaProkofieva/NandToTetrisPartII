import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class JackTokenizer {

    private static final List<Character> VALID_SYMBOLS =
            List.of('{', '}', '(', ')', '[', ']', '.', ',', ';', '+', '-', '*', '/', '&', '|', '<', '>', '=', '~');

    private BufferedReader reader;
    private String currentToken;
    private TokenType currentType;
    private Integer nextChar;

    public JackTokenizer(File file) {
        try {
            reader = new BufferedReader(new FileReader(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean hasMoreTokens() throws IOException {
        return reader.ready();
    }

    public void advance() throws IOException {
        int current;
        if (nextChar != null) {
            if (nextChar == -1) {
                currentType = null;
                currentToken = null;
                return;
            }
            current = nextChar;
        } else {
            current = reader.read();
        }

        // remove whitespaces and comments
        boolean notRealDivision = true;
        while (current != -1 && (Character.isWhitespace(current) || (current == '/' && notRealDivision))) {
            // remove whitespaces
            while (current != -1 && Character.isWhitespace(current)) {
                current = reader.read();
            }

            // remove comments
            while (current == '/' && notRealDivision) {
                nextChar = reader.read();
                if (nextChar == '/') {
                    while (current != -1 && String.valueOf((char) current).matches(".")) {
                        current = reader.read();
                    }
                    if (current != -1) {
                        current = reader.read();
                    }
                } else if (nextChar == '*') {
                    boolean shouldStop = false;
                    while (!shouldStop) {
                        while (current != -1 && current != '*') {
                            current = reader.read();
                        }
                        if (current != -1) {
                            current = reader.read();
                        }
                        if (current == '/') {
                            shouldStop = true;
                            current = reader.read();
                        }
                    }
                } else {
                    notRealDivision = false;
                }
            }
        }

        StringBuilder tokenBuilder = new StringBuilder();
        if (VALID_SYMBOLS.contains((char) current)) {
            currentToken = String.valueOf((char) current);
            currentType = TokenType.SYMBOL;
            current = reader.read();
        } else if (Character.isDigit(current)) {
            while (current != -1 && Character.isDigit(current)) {
                tokenBuilder.append((char) current);
                current = reader.read();
            }
            currentToken = tokenBuilder.toString();
            currentType = TokenType.INT_CONST;
        } else if ('\"' == current) {
            current = reader.read();
            while (current != -1 && current != '\"') {
                tokenBuilder.append((char) current);
                current = reader.read();
            }
            if (current == '\"') {
                current = reader.read();
            }
            currentToken = tokenBuilder.toString();
            currentType = TokenType.STRING_CONST;
        } else {
            while (current != -1 && (Character.isLetterOrDigit(current) || '_' == current)) {
                tokenBuilder.append((char) current);
                current = reader.read();
            }
            currentToken = tokenBuilder.toString();
            if (Arrays.stream(Keyword.values()).anyMatch(k -> k.name().equals(currentToken.toUpperCase()))) {
                currentType = TokenType.KEYWORD;
            } else {
                currentType = TokenType.IDENTIFIER;
            }
        }

        nextChar = current;
    }

    public TokenType tokenType() {
        return currentType;
    }

    public Keyword keyword() {
        if (currentType != TokenType.KEYWORD) {
            throw new IllegalStateException("Current token is not a KEYWORD");
        }
        return Keyword.valueOf(currentToken.toUpperCase());
    }

    public char symbol() {
        if (currentType != TokenType.SYMBOL) {
            throw new IllegalStateException("Current token is not a SYMBOL");
        }
        return currentToken.charAt(0);
    }

    public String identifier() {
        if (currentType != TokenType.IDENTIFIER) {
            throw new IllegalStateException("Current token is not a IDENTIFIER");
        }
        return currentToken;
    }

    public int intVal() {
        if (currentType != TokenType.INT_CONST) {
            throw new IllegalStateException("Current token is not a INT_CONST");
        }
        return Integer.parseInt(currentToken);
    }

    public String stringVal() {
        if (currentType != TokenType.STRING_CONST) {
            throw new IllegalStateException("Current token is not a STRING_CONST");
        }
        return currentToken;
    }
}
