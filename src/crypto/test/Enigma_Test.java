package crypto.test;

import crypto.enigma.Rotor;
import crypto.enigma.Enigma;

public class Enigma_Test {
    public static void main(String[] args) {

        // Encrypt a message with Enigma
        String message = "helloworld";

        // Create the rotors for the Enigma machine
        Rotor rotor1 = new Rotor(new int[]{4, 10, 12, 5, 11, 6, 3, 16, 21, 25, 13, 19, 14, 22, 24, 7, 23, 20, 18, 15, 0, 8, 1, 17, 2, 9}, 0, 0);
        Rotor rotor2 = new Rotor(new int[]{0, 9, 3, 10, 18, 8, 17, 20, 23, 1, 11, 7, 22, 19, 12, 2, 16, 6, 25, 13, 15, 24, 5, 21, 14, 4}, 1, 21);
        Rotor rotor3 = new Rotor(new int[]{1, 3, 5, 7, 9, 11, 2, 15, 17, 19, 23, 21, 25, 13, 24, 4, 8, 22, 6, 0, 10, 12, 20, 18, 16, 14}, 2, 23);

        // Create a new Enigma machine with the default settings
        Enigma enigma = new Enigma(rotor1, rotor2, rotor3);

        // Encrypt the message
        String encryptedMessage = enigma.encryptMessage(message);
        System.out.println("Encrypted message: " + encryptedMessage);

        // Decrypt the message
        String decryptedMessage = enigma.decryptMessage(encryptedMessage);
        System.out.println("Decrypted message: " + decryptedMessage);


    }
}
