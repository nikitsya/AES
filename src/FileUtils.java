import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class FileUtils {

    public static void main(String[] args) {
        String plain_text_path = "plaintext.txt";
        String cipher_text_path = "ciphertext.txt";
        byte[] key = AESUtils.generate16ByteRandomKey();

        System.out.println(getLines(plain_text_path));
    }

    public static List<String> getLines(String path) {
        try {
            return Files.readAllLines(Paths.get(path));
        } catch (IOException e) {
            System.out.println("Error while reading file: " + e);
        }
        return null;
    }
}
