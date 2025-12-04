import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class FileUtils {

    public static void main(String[] args) {
        String plain_text_path = "plaintext.txt";
        String cipher_text_path = "ciphertext.txt";
        byte[] key = AESUtils.generate16ByteRandomKey();

        String content = getFileContent(plain_text_path);

        String cipher_text = AESUtils.encryptOrDecryptAES(content, key, true);
        System.out.println("Encrypted: " + content);
        String returned_text = AESUtils.encryptOrDecryptAES(cipher_text, key, false);
        System.out.println("Plain text: " + returned_text);
    }

    public static String getFileContent(String path) {
        try {
            return Files.readString(Paths.get(path));
        } catch (IOException e) {
            System.out.println("Error while reading file: " + e);
        }
        return null;
    }

    public static void writeFile(String path, String content) {
        try {
            Files.write(Paths.get(path), content.getBytes());
        } catch (IOException e) {
            System.out.println("Error while writing file: " + e);
        }
    }
}
