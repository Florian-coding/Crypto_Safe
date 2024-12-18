package crypto;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class HMAC {
    // Constant defining the HMAC algorithm used
    public static final String ALGORITHM = "HmacSHA256";
    public static final String PEPPER = "Crocodile22!";

    // Method to calculate the HMAC of a given string
    public static String calculateHMac(String key, String password) throws Exception {
        // Initialize the Mac object with the HMAC algorithm
        Mac sha256_HMAC = Mac.getInstance(ALGORITHM);

        // Create a secret key from the provided key and algorithm
        SecretKeySpec secret_key = new SecretKeySpec(key.getBytes("UTF-8"), ALGORITHM);

        // Initialize the Mac with the secret key
        sha256_HMAC.init(secret_key);

        // Generate a salt (number) and add salt and pepper to the password
        int salt = NumberGenerator.generate();
        password = salt + password + PEPPER;

        // Compute the first HMAC hash with salt and pepper
        byte[] firstHash = sha256_HMAC.doFinal(password.getBytes("UTF-8"));

        // Re-hash the result of the first hash
        byte[] secondHash = sha256_HMAC.doFinal(firstHash);

        // Convert the final hash to hexadecimal so that special characters can be displayed
        return byteArrayToHex(secondHash);
    }

    // Method to convert a byte array into a hexadecimal string
    public static String byteArrayToHex(byte[] array) {
        // StringBuilder to construct the hexadecimal string
        // sb => stringbuilder
        StringBuilder sb = new StringBuilder(array.length * 2);

        // Iterate over each byte and format it as two hexadecimal characters
        for(byte b: array)
            sb.append(String.format("%02x", b));

        // Return the hexadecimal string
        return sb.toString();
    }

    // Checking method between two hatchings
    public static boolean checkSum(String hash1, String hash2) {
        if(hash1.equals(hash2))
            return true;
        return false;
    }

    public static void main(String[] args) throws Exception {}
}
