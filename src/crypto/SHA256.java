package crypto;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA256 {
    private static String ALGORITHM = "SHA-256";

    public static String hash(String input) {
        MessageDigest md;
        byte[] byteData;
        String hashed = "";
        int iterator;

        // Causes an exception if provided algorithm doesn't exist
        // (should never throw exception here)
        try {
            md = MessageDigest.getInstance(ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            return hashed;
        }

        // Pass the bytes into the message digest
        md.update(input.getBytes());

        // Computes hash
        byteData = md.digest();

        // Converts byte array to hexadecimal string
        for (iterator = 0; iterator < byteData.length; iterator++) {

            // AND FF and adds 0x100 to convert possible negative bytes to positives one
            int postiveByte = (byteData[iterator] & 0xff) + 0x100;

            // Remove first character (the 0x100)
            String byteString = Integer.toString(postiveByte, 16).substring(1);

            hashed += byteString;
        }

        return hashed;
    }

    // Checks if the hash of two files is identical
    public static boolean CheckSum(String hash1, String hash2) {
        return (hash1.equals(hash2));
    }
}