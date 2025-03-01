import java.io.*;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class CodeWriter implements Closeable {

    private PrintWriter writer;
    private String filename;
    private final AtomicInteger logicOperatorCounter = new AtomicInteger(0);
    private final AtomicInteger returnFunctionCounter = new AtomicInteger(0);

    public CodeWriter(Path path) {
        try {
            this.writer = new PrintWriter(new FileWriter(String.valueOf(path)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String name = path.getFileName().toString();
        this.filename = name.substring(0, name.length() - 3);
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public void writeInit() throws IOException {
        writer.println("@256");
        writer.println("D=A");
        writer.println("@SP");
        writer.println("M=D");
        writeCall("Sys.init", 0);
    }

    public void writeArithmetic(String command) {
        writer.println("// " + command);
        ArithmeticType arithmeticType = ArithmeticType.valueOf(command.toUpperCase());
        switch (arithmeticType) {
            case ADD, SUB, AND, OR -> {
                writePushPop(CommandType.C_POP, SegmentType.TEMP.getSegmentName(), 0);
                writePushPop(CommandType.C_POP, SegmentType.TEMP.getSegmentName(), 1);
                writer.println("@5");
                writer.println("D=M");
                writer.println("@6");
                if (arithmeticType == ArithmeticType.ADD) {
                    writer.println("M=D+M");
                } else if (arithmeticType == ArithmeticType.SUB) {
                    writer.println("M=M-D");
                } else if (arithmeticType == ArithmeticType.AND) {
                    writer.println("M=D&M");
                } else {
                    writer.println("M=D|M");
                }
                writePushPop(CommandType.C_PUSH, SegmentType.TEMP.getSegmentName(), 1);
            }
            case NEG, NOT -> {
                writePushPop(CommandType.C_POP, SegmentType.TEMP.getSegmentName(), 0);
                writer.println("@5");
                if (arithmeticType == ArithmeticType.NEG) {
                    writer.println("M=-M");
                } else {
                    writer.println("M=!M");
                }
                writePushPop(CommandType.C_PUSH, SegmentType.TEMP.getSegmentName(), 0);
            }
            case EQ, GT, LT -> {
                int cnt = logicOperatorCounter.getAndIncrement();
                writePushPop(CommandType.C_POP, SegmentType.TEMP.getSegmentName(), 0);
                writePushPop(CommandType.C_POP, SegmentType.TEMP.getSegmentName(), 1);
                writer.println("@5");
                writer.println("D=M");
                writer.println("@6");
                writer.println("D=M-D");
                writer.println("@TRUE%d".formatted(cnt));
                if (arithmeticType == ArithmeticType.EQ) {
                    writer.println("D;JEQ");
                } else if (arithmeticType == ArithmeticType.GT) {
                    writer.println("D;JGT");
                } else {
                    writer.println("D;JLT");
                }
                writer.println("D=0");
                writer.println("@END%d".formatted(cnt));
                writer.println("0;JMP");
                writer.println("(TRUE%d)".formatted(cnt));
                writer.println("D=-1");
                writer.println("(END%d)".formatted(cnt));
                writer.println("@5");
                writer.println("M=D");
                writePushPop(CommandType.C_PUSH, SegmentType.TEMP.getSegmentName(), 0);
            }
        }
    }

    public void writePushPop(CommandType command, String segment, int index) {
        writer.println("// %s %s %d".formatted(command, segment, index));
        SegmentType segmentType = SegmentType.getByName(segment);
        String segmentRepr = switch (segmentType) {
            case ARG, LCL, THIS, THAT -> segmentType.toString();
            case STAT -> "%s.%d".formatted(filename, index);
            case TEMP -> "5";
            case PNTR -> "THIS";
            default -> null;
        };

        if (command == CommandType.C_PUSH) {
            if (segmentType != SegmentType.STAT) {
                writer.println("@%d".formatted(index));
                writer.println("D=A");
            }
            if (segmentType != SegmentType.CONST) {
                writer.println("@%s".formatted(segmentRepr));
                if (segmentType != SegmentType.TEMP && segmentType != SegmentType.PNTR
                        && segmentType != SegmentType.STAT) {
                    writer.println("A=M");
                }
                if (segmentType != SegmentType.STAT) {
                    writer.println("D=D+A");
                    writer.println("A=D");
                }
                writer.println("D=M");
            }
            writer.println("@SP");
            writer.println("A=M");
            writer.println("M=D");
            writer.println("@SP");
            writer.println("M=M+1");
        } else if (command == CommandType.C_POP) {
            if (segmentType == SegmentType.CONST){
                throw new IllegalArgumentException("POP for constant if not allowed");
            }
            writer.println("@%d".formatted(index));
            writer.println("D=A");
            writer.println("@%s".formatted(segmentRepr));
            if (segmentType != SegmentType.TEMP && segmentType != SegmentType.PNTR
                    && segmentType != SegmentType.STAT) {
                writer.println("A=M");
            }
            if (segmentType != SegmentType.STAT) {
                writer.println("D=D+A");
            } else {
                writer.println("D=A");
            }
            writer.println("@7");
            writer.println("M=D");
            writer.println("@SP");
            writer.println("M=M-1");
            writer.println("A=M");
            writer.println("D=M");
            writer.println("@7");
            writer.println("A=M");
            writer.println("M=D");
        }
    }

    public void writeLabel(String label) {
        writer.println("// label " + label);
        writer.println("(%s)".formatted(label));
    }

    public void writeGoto(String label) {
        writer.println("// goto " + label);
        writer.println("@%s".formatted(label));
        writer.println("0;JMP");
    }

    public void writeIf(String label) {
        writer.println("// if-goto " + label);
        writePushPop(CommandType.C_POP, SegmentType.TEMP.getSegmentName(), 0);
        writer.println("@5");
        writer.println("D=M");
        writer.println("@%s".formatted(label));
        writer.println("D+1;JEQ");
    }

    public void writeFunction(String functionName, int numArgs) {
        writer.println("// function " + functionName + " " + numArgs);
        writeLabel(functionName);
        writer.println("@5");
        writer.println("M=0");
        for (int i = 0; i < numArgs; i++) {
            writePushPop(CommandType.C_PUSH, SegmentType.TEMP.getSegmentName(), 0);
        }
    }

    public void writeCall(String functionName, int numArgs) {
        writer.println("// call " + functionName + " " + numArgs);
        List<String> context = List.of("@LCL", "@ARG", "@THIS", "@THAT");
        int returnNum = returnFunctionCounter.getAndIncrement();
        writer.println("// push return address");
        writer.println("@%s$ret.%d".formatted(functionName, returnNum));
        writer.println("D=A");
        writer.println("@5");
        writer.println("M=D");
        writePushPop(CommandType.C_PUSH, SegmentType.TEMP.getSegmentName(), 0);
        for (String c : context) {
            writer.println("// push " + c);
            writer.println(c);
            writer.println("D=M");
            writer.println("@5");
            writer.println("M=D");
            writePushPop(CommandType.C_PUSH, SegmentType.TEMP.getSegmentName(), 0);
        }
        writer.println("// ARG = SP - 5 - nArgs");
        writer.println("@5");
        writer.println("D=A");
        writer.println("@%d".formatted(numArgs));
        writer.println("D=D+A");
        writer.println("@SP");
        writer.println("D=M-D");
        writer.println("@ARG");
        writer.println("M=D");

        writer.println("// LCL = SP");
        writer.println("@SP");
        writer.println("D=M");
        writer.println("@LCL");
        writer.println("M=D");

        writeGoto(functionName);
        writeLabel("%s$ret.%d".formatted(functionName, returnNum));
    }

    public void writeReturn() {
        writer.println("// return");
        writer.println("// endFrame = LCL");
        writer.println("@LCL");
        writer.println("D=M");

        writer.println("// retAddr = *(endFrame - 5)");
        writer.println("@5");
        writer.println("M=D");
        writer.println("D=D-A");
        writer.println("A=D");
        writer.println("D=M");
        writer.println("@6");
        writer.println("M=D");

        writer.println("// *ARG = pop()");
        writePushPop(CommandType.C_POP, SegmentType.ARG.getSegmentName(), 0);

        writer.println("// SP = ARG + 1");
        writer.println("@ARG");
        writer.println("D=M+1");
        writer.println("@SP");
        writer.println("M=D");

        writer.println("// THAT = *(endFrame - 1)");
        writer.println("@5");
        writer.println("D=M");
        writer.println("D=D-1");
        writer.println("A=D");
        writer.println("D=M");
        writer.println("@THAT");
        writer.println("M=D");

        writer.println("// THIS = *(endFrame - 2)");
        writer.println("@5");
        writer.println("D=M");
        writer.println("D=D-1");
        writer.println("D=D-1");
        writer.println("A=D");
        writer.println("D=M");
        writer.println("@THIS");
        writer.println("M=D");

        writer.println("// ARG = *(endFrame - 3)");
        writer.println("@5");
        writer.println("D=M");
        writer.println("D=D-1");
        writer.println("D=D-1");
        writer.println("D=D-1");
        writer.println("A=D");
        writer.println("D=M");
        writer.println("@ARG");
        writer.println("M=D");

        writer.println("// LCL = *(endFrame - 4)");
        writer.println("@5");
        writer.println("D=M");
        writer.println("D=D-1");
        writer.println("D=D-1");
        writer.println("D=D-1");
        writer.println("D=D-1");
        writer.println("A=D");
        writer.println("D=M");
        writer.println("@LCL");
        writer.println("M=D");

        writer.println("// goto retAddr");
        writer.println("@6");
        writer.println("A=M");
        writer.println("0;JMP");
    }

    public void close() {
        writer.close();
    }

}
