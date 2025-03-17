import enums.SymbolKind;

import java.util.HashMap;
import java.util.Map;

public class SymbolTable {

    private final Map<String, Symbol> classTable;
    private final Map<String, Symbol> subroutineTable;
    private int staticCounter;
    private int fieldCounter;
    private int argCounter;
    private int varCounter;

    public SymbolTable() {
        this.classTable = new HashMap<>();
        this.subroutineTable = new HashMap<>();
        this.staticCounter = 0;
        this.fieldCounter = 0;
        this.argCounter = 0;
        this.varCounter = 0;
    }

    public void startSubroutine() {
        this.subroutineTable.clear();
        this.argCounter = 0;
        this.varCounter = 0;
    }

    public void define(String name, String type, SymbolKind kind) {
        switch (kind) {
            case STATIC -> classTable.put(name, new Symbol(name, type, kind, staticCounter++));
            case FIELD -> classTable.put(name, new Symbol(name, type, kind, fieldCounter++));
            case ARG -> subroutineTable.put(name, new Symbol(name, type, kind, argCounter++));
            case VAR -> subroutineTable.put(name, new Symbol(name, type, kind, varCounter++));
            default -> throw new IllegalArgumentException("Invalid symbol type: %s".formatted(kind));
        }
    }

    public int varCount(SymbolKind kind) {
        return switch (kind) {
            case STATIC -> staticCounter;
            case FIELD -> fieldCounter;
            case VAR -> varCounter;
            case ARG -> argCounter;
            default -> throw new IllegalArgumentException("Invalid symbol type: %s".formatted(kind));
        };
    }

    public SymbolKind kindOf(String name) {
        Symbol symbol = subroutineTable.get(name);
        if (symbol == null) {
            symbol = classTable.get(name);
            if (symbol == null) {
                return SymbolKind.NONE;
            }
        }
        return symbol.getKind();
    }

    public String typeOf(String name) {
        Symbol symbol = subroutineTable.get(name);
        if (symbol == null) {
            symbol = classTable.get(name);
            if (symbol == null) {
                throw new IllegalArgumentException("Unknown name: %s".formatted(name));
            }
        }
        return symbol.getType();
    }

    public int indexOf(String name) {
        Symbol symbol = subroutineTable.get(name);
        if (symbol == null) {
            symbol = classTable.get(name);
            if (symbol == null) {
                throw new IllegalArgumentException("Unknown name: %s".formatted(name));
            }
        }
        return symbol.getIndex();
    }
}
