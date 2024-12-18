import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA256
{

    public static String hash (String message) {

        MessageDigest md;
        byte[] byteData;
        String hashed = "";
        int iterator;

        // Causes an exception if provided algorithm doesn't exist (should never throw exception here)
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            return hashed;
        }

        // Send the message bytes
        md.update(message.getBytes());

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

    // For testing
    public static void main(String[] args) throws Exception
    {
        String hashed = hash("test");
        System.out.println(hashed);
    }
}