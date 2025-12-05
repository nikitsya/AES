import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


public class FileUtils {

    public static String getFileContent(String path) {
        try {
            return Files.readString(Paths.get(path));
        } catch (IOException e) {
            System.out.println("\nError while reading file: " + e);
        }
        return null;
    }

    public static void writeFile(String path, String content) {
        try {
            Files.write(Paths.get(path), content.getBytes());
        } catch (IOException e) {
            System.out.println("\nError while writing file: " + e);
        }
    }
}
