import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;



/**
 * Utility class for AES encryption and decryption using CBC mode with PKCS5 padding.
 * Provides helper methods for converting text to bytes, generating keys,
 * and encoding/decoding encrypted content in Base64.
 */
public class AESUtils {

    /**
     * Encrypts a plaintext string.
     *
     * @param plaintext the text to encrypt
     * @param key       a 16-byte AES key
     * @return Base64-encoded encrypted text, or null if an error occurs
     */
    public static String encryptAES(String plaintext, byte[] key) {
        return encryptOrDecryptAES(plaintext, key, true);
    }

    /**
     * Decrypts a Base64-encoded AES ciphertext.
     *
     * @param ciphertext Base64-encoded encrypted text
     * @param key        a 16-byte AES key
     * @return decrypted plaintext, or null if an error occurs
     */
    public static String decryptAES(String ciphertext, byte[] key) {
        return encryptOrDecryptAES(ciphertext, key, false);
    }

    /**
     * Internal helper method that performs both AES encryption and decryption.
     *
     * @param text    the plaintext (encrypt) or Base64 ciphertext (decrypt)
     * @param key     a 16-byte AES key
     * @param encrypt true to encrypt, false to decrypt
     * @return result string (Base64 for encryption, plaintext for decryption)
     */
    private static String encryptOrDecryptAES(String text, byte[] key, boolean encrypt) {
        String error_message = "\nError while " + (encrypt ? "encrypting" : "decrypting") + ": ";

        try {
            // Create default byte array
            byte[] iv = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
            IvParameterSpec iv_spec = new IvParameterSpec(iv);

            // Create SecretKeyFactory object
            SecretKeySpec secret_key = new SecretKeySpec(key, "AES");

            // CBC mode with PKCS5 padding
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

            if (encrypt) {
                // Encrypt plaintext bytes → Base64
                cipher.init(Cipher.ENCRYPT_MODE, secret_key, iv_spec);
                return Base64.getEncoder().encodeToString(cipher.doFinal(textToBytes(text)));
            } else {
                // Decode Base64 → decrypt → UTF-8 text
                cipher.init(Cipher.DECRYPT_MODE, secret_key, iv_spec);
                return new String(cipher.doFinal(Base64.getDecoder().decode(text)), StandardCharsets.UTF_8);
            }

        } catch (Exception e) {
            System.out.println(error_message + e);
        }

        return null;
    }

    /**
     * Converts a String into UTF-8 encoded bytes.
     *
     * @param text the input string
     * @return byte array representing the UTF-8 encoding
     */
    public static byte[] textToBytes(String text) {
        return text.getBytes(StandardCharsets.UTF_8);
    }

    /**
     * Converts a UTF-8 byte array into a readable String.
     *
     * @param bytes the byte array to convert
     * @return the resulting UTF-8 string
     */
    public static String bytesToString(byte[] bytes) {
        return new String(bytes, java.nio.charset.StandardCharsets.UTF_8);
    }

    /**
     * Generates a secure random 16-byte AES key.
     *
     * @return a random 16-byte array suitable for AES-128
     */
    public static byte[] generate16ByteRandomKey() {
        byte[] key = new byte[16];
        new SecureRandom().nextBytes(key);
        return key;
    }
}
