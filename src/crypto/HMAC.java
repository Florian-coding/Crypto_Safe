package crypto;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class HMAC {
    // Constant defining the HMAC algorithm used
    private static final String ALGORITHM = "HmacSHA256";
    private  static final String SALT = "crypto";
    private static final String PEPPER = "Crocodile22!";
    private static final String CHARSET = "UTF-8";

    // Method to calculate the HMAC of a given string
    public static String hash(String input, String key) {

        try {
            // Initialize the Mac object with the HMAC algorithm
            Mac sha256_HMAC = Mac.getInstance(ALGORITHM);

            // Create a secret key from the provided key and algorithm
            SecretKeySpec secret_key = new SecretKeySpec(key.getBytes(CHARSET), ALGORITHM);

            // Initialize the Mac with the secret key
            sha256_HMAC.init(secret_key);

            // add salt and pepper to the input
            input = SALT + input + PEPPER;

            // Compute the first HMAC hash with salt and pepper
            byte[] firstHash = sha256_HMAC.doFinal(input.getBytes(CHARSET));

            // Re-hash the result of the first hash
            byte[] secondHash = sha256_HMAC.doFinal(firstHash);

            // Convert the final hash to hexadecimal so that special characters can be
            // displayed
            return byteArrayToHex(secondHash);
        } catch (Exception e) {
            System.out.println("Erreur lors du hachage: " + e.toString());
            return "";
        }
    }

    // Checking method between two hatchings
    public static boolean CheckSum(String hash1, String hash2) {
        return hash1.equals(hash2);
    }

    // Method to convert a byte array into a hexadecimal string
    private static String byteArrayToHex(byte[] array) {
        // StringBuilder to construct the hexadecimal string
        // sb => stringbuilder
        StringBuilder sb = new StringBuilder(array.length * 2);

        // Iterate over each byte and format it as two hexadecimal characters
        for (byte b : array) {
            sb.append(String.format("%02x", b));
        }

        // Return the hexadecimal string
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println(hash("abc", "abc"));
        System.out.println(hash("abc", "abc"));
    }
}