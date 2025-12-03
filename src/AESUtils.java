import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Arrays;

public class AESUtils {

    public static void main(String[] args) {

        String text = "test";
        byte[] textToBytes = textToBytes(text);
        System.out.println(text + ": " + Arrays.toString(textToBytes));

        byte[] key128 = generateRandomKey();
        System.out.println("128-bit key: " + Arrays.toString(key128));

    }

    public static String encrypt(String text) {
        try {

        } catch (Exception e) {
            System.out.println("Error while encrypting: " + e);
        }

        return null;
    }

    public static String decrypt(String text) {
        try {

        } catch (Exception e) {
            System.out.println("Error while decrypting: " + e);
        }

        return null;
    }

    public static byte[] textToBytes(String text) {
        return text.getBytes(StandardCharsets.UTF_8);
    }

    public static byte[] generateRandomKey() {
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        return key;
    }

}
