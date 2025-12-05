import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


/**
 * Utility class for file operations such as reading,
 * writing, and checking file existence.
 */
public class FileUtils {

    /**
     * Reads the content of the file at the specified path as a string.
     *
     * @param path the path to the file to read
     * @return the file content as a String, or null if an error occurs
     */
    public static String getFileContent(String path) {
        try {
            return Files.readString(Paths.get(path));
        } catch (IOException e) {
            System.out.println("Error while reading file: " + e);
        }
        return null;
    }

    /**
     * Writes the given string content to a file at the specified path.
     * If the file does not exist, it will be created.
     *
     * @param path the file path where content will be written
     * @param content the text to write to the file
     */
    public static void writeFile(String path, String content) {
        try {
            Files.write(Paths.get(path), content.getBytes());
        } catch (IOException e) {
            System.out.println("Error while writing file: " + e);
        }
    }

    /**
     * Checks whether a file with the given name exists.
     *
     * @param file_name the name or path of the file to check
     * @return true if the file exists, false otherwise
     */
    public static boolean checkFileExistence(String file_name) {
        return new java.io.File(file_name).exists();
    }
}
