import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) {
        String filename = args[0];
        File file = new File(filename);
        boolean isDirectory = file.isDirectory();

        String outputFilename =
                isDirectory
                        ? filename + "/" + file.getName() + ".asm"
                        : filename.substring(0, filename.length() - 3) + ".asm";
        try (CodeWriter codeWriter = new CodeWriter(Path.of(outputFilename))){
            codeWriter.writeInit();
            if (isDirectory) {
                File[] files = file.listFiles((dir, name) -> name.endsWith(".vm"));
                if (files == null) {
                    throw new IllegalArgumentException("Error on reading directory");
                }

                for (File f : files) {
                    String fname = f.getName();
                    fname = fname.substring(0, fname.length() - 3);
                    codeWriter.setFilename(fname);
                    translateFile(f, codeWriter);
                }
            } else {
                if (!filename.endsWith(".vm")) {
                    throw new IllegalArgumentException("Invalid filename!");
                }
                translateFile(file, codeWriter);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void translateFile(File file, CodeWriter codeWriter) throws IOException {
        Parser parser = new Parser(file);
        while (parser.hasMoreCommands()) {
            String vmCommand = parser.advance();
            switch (parser.commandType()) {
                case C_ARITHMETIC -> codeWriter.writeArithmetic(vmCommand);
                case C_POP, C_PUSH ->
                        codeWriter.writePushPop(
                                parser.commandType(),
                                parser.arg1(),
                                Integer.parseInt(parser.arg2())
                        );
                case C_LABEL -> codeWriter.writeLabel(parser.arg1());
                case C_GOTO -> codeWriter.writeGoto(parser.arg1());
                case C_IF -> codeWriter.writeIf(parser.arg1());
                case C_FUNCTION -> codeWriter.writeFunction(parser.arg1(), Integer.parseInt(parser.arg2()));
                case C_CALL -> codeWriter.writeCall(parser.arg1(), Integer.parseInt(parser.arg2()));
                case C_RETURN -> codeWriter.writeReturn();
            }
        }
    }
}
