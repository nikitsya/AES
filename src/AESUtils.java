import java.nio.charset.StandardCharsets;

public class AESUtils {

    public static void main(String[] args) {

        String text = "test";
        String hexText = toHex(text);
        System.out.println(text + ": " + hexText);
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
}
