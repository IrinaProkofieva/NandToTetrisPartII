import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class Parser {

    private BufferedReader reader;
    private String currentCommand;
    private CommandType currentType;
    private String[] currentsParts;

    private final List<CommandType> TYPES_WITH_SECOND_ARG =
            List.of(CommandType.C_PUSH, CommandType.C_POP, CommandType.C_FUNCTION, CommandType.C_CALL);

    public Parser(File file) {
        try {
            reader = new BufferedReader(new FileReader(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean hasMoreCommands() throws IOException {
        if (!reader.ready()) {
            return false;
        }
        currentCommand = reader.readLine();
        while (reader.ready() && (currentCommand.isBlank() || currentCommand.strip().startsWith("//"))) {
            currentCommand = reader.readLine();
        }

        if (currentCommand != null && !currentCommand.isBlank()) {
            currentCommand = currentCommand.split("//", 2)[0].strip();
        }

        return currentCommand != null;
    }

    public String advance() {
        currentType = currentCommand != null ? CommandType.getType(currentCommand) : null;
        currentsParts = currentCommand != null ? currentCommand.split("\\s+") : null;
        return currentCommand;
    }

    public CommandType commandType() {
        return currentType;
    }

    public String arg1() {
        if (currentType == CommandType.C_RETURN) {
            throw new IllegalStateException("Method should not be called on %s type".formatted(currentType));
        }
        if (currentsParts.length == 1) {
            return currentsParts[0];
        }
        return currentsParts[1];
    }

    public String arg2() {
        if (!TYPES_WITH_SECOND_ARG.contains(currentType)) {
            throw new IllegalStateException("Method should not be called on %s type".formatted(currentType));
        }
        if (currentsParts.length < 3) {
            return null;
        }
        return currentsParts[2];
    }

}
