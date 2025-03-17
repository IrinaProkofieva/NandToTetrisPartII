package enums;

public enum SymbolKind {
    STATIC(Segment.STATIC),
    FIELD(Segment.THIS),
    ARG(Segment.ARGUMENT),
    VAR(Segment.LOCAL),
    NONE;

    private final Segment segment;

    SymbolKind(Segment segment) {
        this.segment = segment;
    }

    SymbolKind(){
        this.segment = null;
    }

    public Segment getSegment() {
        return segment;
    }
}
