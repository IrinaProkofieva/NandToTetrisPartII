import java.util.Arrays;
import java.util.List;

public enum CommandType {
    C_ARITHMETIC(Arrays.stream(ArithmeticType.values()).map(s -> s.name().toLowerCase()).toList(), 1),
    C_PUSH(List.of("push"), 3),
    C_POP(List.of("pop"), 3),
    C_LABEL(List.of("label"), 2),
    C_GOTO(List.of("goto"), 2),
    C_IF(List.of("if-goto"), 2),
    C_FUNCTION(List.of("function"), 3),
    C_RETURN(List.of("return"), 1),
    C_CALL(List.of("call"), 3);

    private final List<String> commands;
    private final int numberOfParts;

    CommandType(List<String> commands, int numberOfParts) {
        this.commands = commands;
        this.numberOfParts = numberOfParts;
    }

    public static CommandType getType(String command) {
        String[] parts = command.split("\\s+");
        return Arrays.stream(CommandType.values())
                .filter(s -> s.commands.contains(parts[0]) && parts.length == s.numberOfParts)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("CommandType for %s was not found".formatted(parts[0])));
    }
}
