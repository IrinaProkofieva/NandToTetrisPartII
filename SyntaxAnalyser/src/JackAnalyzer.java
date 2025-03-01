import java.io.File;
import java.io.IOException;

public class JackAnalyzer {

    public static void main(String[] args) throws IOException {

        String filename = args[0];
        File file = new File(filename);
        boolean isDirectory = file.isDirectory();
        if (isDirectory) {
            File[] files = file.listFiles((dir, name) -> name.endsWith(".jack"));
            if (files == null) {
                throw new IllegalArgumentException("Error on reading directory");
            }

            for (File f : files) {
                compileFile(f);
            }
        } else {
            compileFile(file);
        }
    }

    private static void compileFile(File file) throws IOException {
        if (!file.getName().endsWith(".jack")) {
            throw new IllegalArgumentException("Invalid filename!");
        }
        CompilationEngine engine = new CompilationEngine(file);
        engine.compileClass();
    }
}
