package crypto;

import utils.Verification;

public class Vigenere {
    public static void main(String[] args) {
        
    }

    // Function that encrypts a phrase by taking two parameters:
    // inputPhrase = the phrase to encrypt
    // key = the encryption key
    public static String encrypt(String inputPhrase, String key) {

        // Declare a StringBuilder to store the encrypted phrase
        StringBuilder encryptedResult = new StringBuilder();
        int aAscii = 97; // ASCII value for 'a'

        // Loop through the phrase to process each character
        for (int i = 0; i < inputPhrase.length(); i++) {
            // Store a character of the key based on the current index
            // The i % key.length() repeats the key if needed
            char keyChar = key.charAt(i % key.length());

            // Store a character of the phrase based on the current index
            char phraseChar = inputPhrase.charAt(i);

            // Perform encryption using the mathematical formula:
            // ( (plainChar - a) + (keyChar - a)) % 26 + a )
            char encryptedChar = (char) ((phraseChar - aAscii + (keyChar - aAscii)) % 26 + aAscii);

            // Append each encrypted character to the result
            encryptedResult.append(encryptedChar);
        }

        return encryptedResult.toString();
    }

    public static String decrypt(String encryptedPhrase, String key) {
        // StringBuilder to store the decrypted phrase
        StringBuilder decryptedResult = new StringBuilder();
        int aAscii = 97; // ASCII value of 'a'

        // Loop through the encrypted phrase to process each character
        for (int i = 0; i < encryptedPhrase.length(); i++) {
            // Get the corresponding character from the key
            // (The key repeats itself if needed using i % key.length())
            char keyChar = key.charAt(i % key.length());

            // Get the character from the encrypted phrase
            char phraseChar = encryptedPhrase.charAt(i);

            // Perform decryption using the formula:
            // ( (encryptedChar - a) - (keyChar - a) + 26 ) % 26 + a
            // This reverses the encryption process and handles negative values with +26
            char decryptedChar = (char) ((phraseChar - aAscii - (keyChar - aAscii) + 26) % 26 + aAscii);

            // Append each decrypted character to the result
            decryptedResult.append(decryptedChar);
        }

        // Return the fully decrypted phrase as a String
        return decryptedResult.toString();
    }
}
