import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Arrays;

public class AESUtils {

    public static void main(String[] args) {

        String text = "test";
        String hexText = toHex(text);
        System.out.println(text + ": " + hexText);

        byte[] key128 = generateRandomKey(128);
        System.out.println("128-bit key: " + toHex(Arrays.toString(key128)));

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

    public static String toHex(String text) {
        StringBuilder sb = new StringBuilder();
        for (byte b : text.getBytes(StandardCharsets.UTF_8)) {
            sb.append(String.format("%02X", b));
        }
        return sb.toString();
    }

    public static byte[] generateRandomKey(int bits) {
        if (bits % 8 != 0) {
            throw new IllegalArgumentException("Key size must be a multiple of 8 bits");
        }

        int bytes = bits / 8;
        SecureRandom random = new SecureRandom();

        byte[] key = new byte[bytes];
        random.nextBytes(key);

        return key;
    }
}
