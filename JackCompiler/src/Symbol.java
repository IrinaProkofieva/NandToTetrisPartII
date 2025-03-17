import enums.SymbolKind;

public class Symbol {
    private final String name;
    private final String type;
    private final SymbolKind kind;
    private final int index;

    public Symbol(
            String name,
            String type,
            SymbolKind kind,
            int index
    ) {
        this.name = name;
        this.type = type;
        this.kind = kind;
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public SymbolKind getKind() {
        return kind;
    }

    public int getIndex() {
        return index;
    }
}
