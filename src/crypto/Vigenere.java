package crypto;

public class Vigenere {
    // Used to encrypt using Vigenere
    public static String encrypt(String input, String key) {

        // Declare a StringBuilder to store the encrypted phrase
        StringBuilder encrypted = new StringBuilder();
        int aAscii = 97; // ASCII value for 'a'

        // Loop through the phrase to process each character
        for (int i = 0; i < input.length(); i++) {
            // Store a character of the key based on the current index
            // The i % key.length() repeats the key if needed
            char keyChar = key.charAt(i % key.length());

            // Store a character of the phrase based on the current index
            char phraseChar = input.charAt(i);

            // Perform encryption using the mathematical formula:
            // ( (plainChar - a) + (keyChar - a)) % 26 + a )
            char encryptedChar = (char) ((phraseChar - aAscii + (keyChar - aAscii)) % 26 + aAscii);

            // Append each encrypted character to the result
            encrypted.append(encryptedChar);
        }

        return encrypted.toString();
    }

    // Used to decrypt using Vigenere
    public static String decrypt(String encrypted, String key) {
        // StringBuilder to store the decrypted phrase
        StringBuilder decrypted = new StringBuilder();
        int aAscii = 97; // ASCII value of 'a'

        // Loop through the encrypted phrase to process each character
        for (int i = 0; i < encrypted.length(); i++) {
            // Get the corresponding character from the key
            // (The key repeats itself if needed using i % key.length())
            char keyChar = key.charAt(i % key.length());

            // Get the character from the encrypted phrase
            char phraseChar = encrypted.charAt(i);

            // Perform decryption using the formula:
            // ( (encryptedChar - a) - (keyChar - a) + 26 ) % 26 + a
            // This reverses the encryption process and handles negative values with +26
            char decryptedChar = (char) ((phraseChar - aAscii - (keyChar - aAscii) + 26) % 26 + aAscii);

            // Append each decrypted character to the result
            decrypted.append(decryptedChar);
        }

        // Return the fully decrypted phrase as a String
        return decrypted.toString();
    }
}
