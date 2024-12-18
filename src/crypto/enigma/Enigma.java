package crypto.enigma;

public class Enigma {
    // Array to store the rotors in the machine
    private final Rotor[] rotors = new Rotor[3];
    //Reflector rotor
    private final Rotor reflector = new Rotor(new int[]{25,24,23,22,21,20,19,18,17,16,15,14,13,12,11,10,9,8,7,6,5,4,3,2,1,0}, 4, 0);
    private final Rotor[] secondRotors = new Rotor[3];


    // Constructor to initialize the Enigma machine with the given rotors
    public Enigma(Rotor firstRotor, Rotor secondRotor, Rotor thirdRotor) {
        this.rotors[firstRotor.getRotorPositionInMachine()] = firstRotor;
        this.rotors[secondRotor.getRotorPositionInMachine()] = secondRotor;
        this.rotors[thirdRotor.getRotorPositionInMachine()] = thirdRotor;

        // Initialize the second set of rotors with random values
        this.secondRotors[0] = new Rotor(new int[]{9,12,3,16,5,14,7,18,10,21,11,24,13,8,17,2,25,4,23,6,15,20,1,22,19,0}, 5, 0);
        this.secondRotors[1] = new Rotor(new int[]{0,6,12,18,24,3,9,15,21,1,7,13,19,25,4,10,16,22,2,8,14,20,5,11,17,23}, 6, 0);
        this.secondRotors[2] = new Rotor(new int[]{6,12,18,24,3,9,15,21,1,7,13,19,25,4,10,16,22,2,8,14,20,5,11,17,23,0}, 7, 0);
    }

    // Method to encrypt a message using the Enigma machine
    public String encryptMessage(String message){
        StringBuilder encryptedMessage = new StringBuilder();
        for (int i = 0; i < message.length(); i++) {
            // Rotate the rotors before encrypting each letter
            rotateRotorsForward();
            char letter = message.charAt(i);
            //Get the index of the letter in the first rotor, when letter passes through the first rotor it gets encrypted
            char encryptedLetterIndex = (char) rotors[0].getLetterIndex(letter - 97); // Minus 97 to get the position of the letter in the alphabet
            //Get the encrypted letter from the first rotor
            char encryptedLetter = (char) rotors[0].getLetters()[encryptedLetterIndex];
            //Pass the encrypted letter through the first rotor to the second and get the new encrypted letter
            encryptedLetter = encryptLetter(encryptedLetter, rotors[0], rotors[1]);
            //Pass the encrypted letter through the second to the third rotor and get a new encrypted letter
            encryptedLetter = encryptLetter(encryptedLetter, rotors[1], rotors[2]);
            //Pass the encrypted letter through the third rotor to the reflector and get a new encrypted letter
            encryptedLetter = encryptLetter(encryptedLetter, rotors[2], reflector);
            //Pass the encrypted letter through the second set of rotors
            encryptedLetter = encryptLetter(encryptedLetter, reflector, secondRotors[0]);
            encryptedLetter = encryptLetter(encryptedLetter, secondRotors[0], secondRotors[1]);
            encryptedLetter = encryptLetter(encryptedLetter, secondRotors[1], secondRotors[2]);
            // Add the encrypted letter to the encrypted message
            encryptedMessage.append((char)(encryptedLetter + 97)); // Add 97 to get the ASCII value of the letter
        }
        return encryptedMessage.toString();
    }

    // Method to decrypt a message using the Enigma machine
    public String decryptMessage(String message){
        StringBuilder decryptedMessage = new StringBuilder();
        for (int i = message.length()-1; i >= 0; i--) {
            char letter = message.charAt(i);
            //Get the index of the letter in the first rotor, when letter passes through the first rotor it gets encrypted
            char decryptedLetterIndex = (char) secondRotors[2].getLetterIndex(letter - 97); // Minus 97 to get the position of the letter in the alphabet
            //Get the encrypted letter from the first rotor
            char decryptedLetter = (char) secondRotors[2].getLetters()[decryptedLetterIndex];
            //Pass the encrypted letter through the first rotor to the second and get the new encrypted letter
            decryptedLetter = encryptLetter(decryptedLetter, secondRotors[2], secondRotors[1]);
            //Pass the encrypted letter through the second to the third rotor and get a new encrypted letter
            decryptedLetter = encryptLetter(decryptedLetter, secondRotors[1], secondRotors[0]);
            //Pass the encrypted letter through the third rotor to the reflector and get a new encrypted letter
            decryptedLetter = encryptLetter(decryptedLetter, secondRotors[0], reflector);
            //Pass the encrypted letter through the second set of rotors
            decryptedLetter = encryptLetter(decryptedLetter, reflector, rotors[2]);
            decryptedLetter = encryptLetter(decryptedLetter, rotors[2], rotors[1]);
            decryptedLetter = encryptLetter(decryptedLetter, rotors[1], rotors[0]);
            // Add the encrypted letter to the encrypted message
            decryptedMessage.append((char)(decryptedLetter + 97)); // Add 97 to get the ASCII value of the letter
            // Rotate backward the rotors before decrypting each letter
            rotateRotorsBackward();
        }
        return decryptedMessage.reverse().toString();
    }


    // Method to rotate forward the rotors in the machine
    public void rotateRotorsForward() {
        // Rotor 2 rotates when Rotor 1 make a full rotation
        if (rotors[0].getNotchPosition() + 1 > rotors[0].getLetters().length - 1) {
            // Rotor 3 rotates when Rotor 2 makes a full rotation
            if (rotors[1].getNotchPosition() + 1 > rotors[1].getLetters().length - 1) {
                rotors[2].rotateForward();
            }
            rotors[1].rotateForward();
        }
        // Rotor 1 rotates every time
        rotors[0].rotateForward();
    }

    public void rotateRotorsBackward() {
        // Rotor 2 rotates when Rotor 1 make a full backward rotation
        if(rotors[0].getNotchPosition() - 1 < 0) {
            // Rotor 3 rotates when Rotor 2 makes a full backward rotation
            if(rotors[1].getNotchPosition() - 1 < 0) {
                rotors[2].rotateBackward();
            }
            rotors[1].rotateBackward();
        }
        // Rotor 1 rotates every time
        rotors[0].rotateBackward();
    }


    // Method to encrypt a letter when it passes through the rotors
    public char encryptLetter(char letter, Rotor inputRotor, Rotor outputRotor) {
        // Get the position of the letter in the input rotor
        int letterIndexInInputRotor = inputRotor.getLetterIndex(letter);
        // Return the letter corresponding to the letterPositionInInputRotor in the output rotor
        return (char) (outputRotor.getLetters()[letterIndexInInputRotor]);
    }
}


