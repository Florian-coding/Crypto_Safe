package crypto;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class AES {

    private static final String SALT = "super_mega_secret_salt";
    private static final String SECRET_KEY_FACTORY_ALGORITHM = "PBKDF2WithHmacSHA256";
    private static final String SECRET_KEY_SPEC_ALGORITHM = "AES";
    private static final String CIPHER_TRANSFORMATION = "AES/CBC/PKCS5Padding";
    private static final int KEY_SPEC_ITERATION = 65536;
    private static final int KEY_SPEC_LENGTH = 256;

    // Encrypts with a secret key
    public static String encrypt(String input, String secretKeyString) {
        try {
            // Generate an initialization vector for cipher
            IvParameterSpec ivspec = generateInitializationVector();

            // Used to process the provided secret key
            SecretKeyFactory factory = SecretKeyFactory.getInstance(SECRET_KEY_FACTORY_ALGORITHM);

            // Mix secret key and salt for specification, with an ammount of iteration and a length of 256 bits
            KeySpec spec = new PBEKeySpec(secretKeyString.toCharArray(), SALT.getBytes(), KEY_SPEC_ITERATION, KEY_SPEC_LENGTH);

            // This generates a *real* secret key for the cipher
            SecretKey tmp = factory.generateSecret(spec);
            SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), SECRET_KEY_SPEC_ALGORITHM);

            // Init cipher in CBC mode with PKCS5 padding
            Cipher cipher = Cipher.getInstance(CIPHER_TRANSFORMATION);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivspec);

            // Encrypts with the secret key using the cipher
            byte[] encryptedBytes = cipher.doFinal(input.getBytes(StandardCharsets.UTF_8));

            // Adds the initialization vector into the crypted input
            byte[] iv = ivspec.getIV();
            byte[] encryptedDataWithIv = new byte[iv.length + encryptedBytes.length];
            System.arraycopy(iv, 0, encryptedDataWithIv, 0, iv.length);
            System.arraycopy(encryptedBytes, 0, encryptedDataWithIv, iv.length, encryptedBytes.length);

            // Return the final encrypted in Base64 with IV
            return Base64.getEncoder().encodeToString(encryptedDataWithIv);
        } catch (Exception e) {
            System.out.println("Erreur lors de l'encryption: " + e.toString());
            return "";
        }
    }

    // Decrypts with a secret key
    public static String decrypt(String encrypted, String secretKeyString) {
        try {
            // Decodes the encrypted into an byte array
            byte[] encryptedDataWithIv = Base64.getDecoder().decode(encrypted);

            // Extract the 16 bytes IV and the encrypted data
            byte[] iv = new byte[16];
            System.arraycopy(encryptedDataWithIv, 0, iv, 0, iv.length);
            byte[] encryptedData = new byte[encryptedDataWithIv.length - iv.length];
            System.arraycopy(encryptedDataWithIv, iv.length, encryptedData, 0, encryptedData.length);

            // Recreate the IV parameters from the extracted 16 bytes IV
            IvParameterSpec ivspec = new IvParameterSpec(iv);

            // Used to process the decryption
            SecretKeyFactory factory = SecretKeyFactory.getInstance(SECRET_KEY_FACTORY_ALGORITHM);

            // Mix secret key and salt for specification, with an ammount of iteration and a length of 256 bits
            KeySpec spec = new PBEKeySpec(secretKeyString.toCharArray(), SALT.getBytes(), KEY_SPEC_ITERATION, KEY_SPEC_LENGTH);
            SecretKey tmp = factory.generateSecret(spec);
            SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), SECRET_KEY_SPEC_ALGORITHM);

            // Init cipher in CBC mode with PKCS5 padding
            Cipher cipher = Cipher.getInstance(CIPHER_TRANSFORMATION);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivspec);

            // Decrypts in a byte array
            byte[] decryptedBytes = cipher.doFinal(encryptedData);

            // Converts the decrypted data array into a string
            return new String(decryptedBytes, StandardCharsets.UTF_8);

        } catch (Exception e) {
            System.out.println("Erreur lors du décryptage: " + e.toString());
            return "";
        }
    }

    // Generates a random initialization vector
    private static IvParameterSpec generateInitializationVector() {
        // 16 bytes standard to AES
        byte[] iv = new byte[16];
        // Fill with random bytes
        new SecureRandom().nextBytes(iv);
        return new IvParameterSpec(iv);
    }
}
