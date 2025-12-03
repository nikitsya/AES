import java.nio.charset.StandardCharsets;

public class AESUtils {

    public static void main(String[] args) {

        String text = "test";
        String binaryText = toBinary(text);
        System.out.println(text + ": " + binaryText);
    }

    // This method use to encrypt
    public static String encrypt(String text) {
        try {

        } catch (Exception e) {
            System.out.println("Error while encrypting: " + e);
        }

        return null;
    }

    // This method use to decrypt
    public static String decrypt(String text) {
        try {

        } catch (Exception e) {
            System.out.println("Error while decrypting: " + e);
        }

        return null;
    }

    public static String toBinary(String text) {
        StringBuilder sb = new StringBuilder();

        // Loop through each byte of the string encoded in UTF-8
        for (byte b : text.getBytes(StandardCharsets.UTF_8)) {
            // Convert the byte to an unsigned binary string (0–255), pad it to 8 bits with leading zeros
            sb.append(String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0'));
        }

        return sb.toString();
    }
}
