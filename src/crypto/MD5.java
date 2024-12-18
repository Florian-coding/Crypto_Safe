package crypto;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {
    public static void main(String[] args) {}

    public static String hash(String password){
        MessageDigest md;
        byte[] byteData;
        String hashed = "";
        int iterator;

        // Causes an exception if provided algorithm doesn't exist (should never throw exception here)
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            return hashed;
        }

        // Send the message bytes
        md.update(password.getBytes());

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
    public static boolean CheckSum(String hash1, String hash2){
        if(hash1.equals(hash2)){
            return true;
        }
        return false;
    }
}
