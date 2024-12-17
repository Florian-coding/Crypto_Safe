package crypto;
public class Rot{
    //Function to encrypt a clear text with a given number of rotations
    public static String encrypt(String clearText, int rotations){
        //Initialize the encrypted text
        String encryptedText = "";
        //Getting the length of the clear text for readability
        int encryptedTextlength = clearText.length();
        //Iterating through the clear text
        for (int i = 0; i < encryptedTextlength; i++) {
            //Getting the ASCII value of the character at index i
            int currentChar = clearText.charAt(i);
            //Minus 97 to get the value of the character in the range 0-25
            //Add the number of rotations
            //Modulo 26 to get the value in the range 0-25
            //Add 97 to get the ASCII value of the encrypted character
            int encryptedChar = (currentChar - 97 + rotations) % 26 + 97;
            //Add the encrypted character to the encrypted text
            encryptedText += (char) (encryptedChar);
        }
        return encryptedText;
    }

    //Function to decrypt an encrypted text with a given number of rotations
    public static String decrypt(String encryptedText, int rotations){
        //Initialize the clear text
        String clearText = "";
        //Getting the length of the encrypted text for readability
        int encryptedTextlength = encryptedText.length();
        //Iterating through the encrypted text
        for (int i = 0; i < encryptedTextlength; i++) {
            //Getting the ASCII value of the character at index i
            int currentChar = encryptedText.charAt(i);
            //Minus 97 to get the value of the character in the range 0-25
            //Subtract the number of rotations modulo 26 to handle value that exceed 26
            //Add 26 to handle negative values
            //Modulo 26 to get the value in the range 0-25
            //Add 97 to get the ASCII value of the clear character
            int clearChar = (currentChar - 97 - (rotations % 26) + 26) % 26 + 97;
            //Add the clear character to the clear text
            clearText += (char) (clearChar);
        }
        return clearText;
    }
}