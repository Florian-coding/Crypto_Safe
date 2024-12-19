package crypto;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class HMAC {
    // Constant defining the HMAC algorithm used
    private static final String ALGORITHM = "HmacSHA256";
    private static final String PEPPER = "Crocodile22!";
    private static final String CHARSET = "UTF-8";

    // Method to calculate the HMAC of a given string
    public static String hash(String password, String key) {

        try {
            // Initialize the Mac object with the HMAC algorithm
            Mac sha256_HMAC = Mac.getInstance(ALGORITHM);

            // Create a secret key from the provided key and algorithm
            SecretKeySpec secret_key = new SecretKeySpec(key.getBytes(CHARSET), ALGORITHM);

            // Initialize the Mac with the secret key
            sha256_HMAC.init(secret_key);

            // Generate a salt (number) and add salt and pepper to the password
            int salt = NumberGenerator.generate();
            password = salt + password + PEPPER;

            // Compute the first HMAC hash with salt and pepper
            byte[] firstHash = sha256_HMAC.doFinal(password.getBytes(CHARSET));

            // Re-hash the result of the first hash
            byte[] secondHash = sha256_HMAC.doFinal(firstHash);

            // Convert the final hash to hexadecimal so that special characters can be displayed
            return byteArrayToHex(secondHash);
        } catch (Exception e) {
            System.out.println("Erreur lors du hashage: " + e.toString());
            return "";
        }
    }
    
    // Checking method between two hatchings
    public static boolean CheckSum(String hash1, String hash2) {
        if(hash1.equals(hash2))
            return true;
        return false;
    }

    // Method to convert a byte array into a hexadecimal string
    private static String byteArrayToHex(byte[] array) {
        // StringBuilder to construct the hexadecimal string
        // sb => stringbuilder
        StringBuilder sb = new StringBuilder(array.length * 2);

        // Iterate over each byte and format it as two hexadecimal characters
        for(byte b: array)
            sb.append(String.format("%02x", b));

        // Return the hexadecimal string
        return sb.toString();
    }
}
