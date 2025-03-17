import enums.Command;
import enums.Segment;

import java.io.Closeable;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class VMWriter implements Closeable {

    private final PrintWriter writer;

    public VMWriter(String outputFilepath) throws IOException {
        writer = new PrintWriter(new FileWriter(outputFilepath));
    }

    public void writePush(Segment segment, int index) {
        writer.println("\tpush %s %d".formatted(segment.name().toLowerCase(), index));
    }

    public void writePop(Segment segment, int index) {
        if (segment == Segment.CONSTANT) {
            throw new IllegalArgumentException("CONSTANT segment can not be popped");
        }
        writer.println("\tpop %s %d".formatted(segment.name().toLowerCase(), index));
    }

    public void writeArithmetics(Command command) {
        writer.println("\t%s".formatted(command.name().toLowerCase()));
    }

    public void writeLabel(String label) {
        writer.println("label %s".formatted(label));
    }

    public void writeGoto(String label) {
        writer.println("\tgoto %s".formatted(label));
    }

    public void writeIf(String label) {
        writer.println("\tif-goto %s".formatted(label));
    }

    public void writeCall(String name, int nArgs) {
        writer.println("\tcall %s %d".formatted(name, nArgs));
    }

    public void writeFunction(String name, int nLocals) {
        writer.println("function %s %d".formatted(name, nLocals));
    }

    public void writeReturn() {
        writer.println("\treturn");
    }

    public void close() {
        writer.close();
    }
}
