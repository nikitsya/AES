import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;


public class AESUtils {

    public static String encryptAES(String plaintext, byte[] key) {
        return encryptOrDecryptAES(plaintext, key, true);
    }

    public static String decryptAES(String ciphertext, byte[] key) {
        return encryptOrDecryptAES(ciphertext, key, false);
    }

    private static String encryptOrDecryptAES(String text, byte[] key, boolean encrypt) {
        String error_message = "\nError while " + (encrypt ? "encrypting" : "decrypting") + ": ";

        try {
            // Create default byte array
            byte[] iv = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
            IvParameterSpec iv_spec = new IvParameterSpec(iv);

            // Create SecretKeyFactory object
            SecretKeySpec secret_key = new SecretKeySpec(key, "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

            if (encrypt) {
                cipher.init(Cipher.ENCRYPT_MODE, secret_key, iv_spec);
                return Base64.getEncoder().encodeToString(cipher.doFinal(textToBytes(text)));
            } else {
                cipher.init(Cipher.DECRYPT_MODE, secret_key, iv_spec);
                return new String(cipher.doFinal(Base64.getDecoder().decode(text)), StandardCharsets.UTF_8);
            }

        } catch (Exception e) {
            System.out.println(error_message + e);
        }

        return null;
    }

    public static byte[] textToBytes(String text) {
        return text.getBytes(StandardCharsets.UTF_8);
    }

    public static String bytesToString(byte[] bytes) {
        return new String(bytes, java.nio.charset.StandardCharsets.UTF_8);
    }

    public static byte[] generate16ByteRandomKey() {
        byte[] key = new byte[16];
        new SecureRandom().nextBytes(key);
        return key;
    }
}
