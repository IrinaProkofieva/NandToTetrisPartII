import java.util.Arrays;

public enum SegmentType {
    LCL("local"),
    ARG("argument"),
    THIS("this"),
    THAT("that"),
    CONST("constant"),
    STAT("static"),
    PNTR("pointer"),
    TEMP("temp")
    ;

    private final String segmentName;

    SegmentType(String segmentName) {
        this.segmentName = segmentName;
    }

    public static SegmentType getByName(String name) {
        return Arrays.stream(SegmentType.values()).filter(s -> s.segmentName.equals(name)).findFirst().orElseThrow();
    }

    public String getSegmentName() {
        return segmentName;
    }
}
