package crypto.enigma;

public class Rotor {
    // Letters and their order in the rotor
    private final int[] letters;
    // Current position of the rotor in the machine
    private final int rotorPositionInMachine;
    // Position where the rotor triggers the next rotor
    private int notchPosition = 0;

    // Constructor to initialize the rotor with a given set of letters
    public Rotor(int[] letters, int rotorPositionInMachine, int notchPosition) {
        this.letters = letters;
        this.rotorPositionInMachine = rotorPositionInMachine;
        this.notchPosition = notchPosition;
    }
    //Because position is private, we need a method to get it
    public int getRotorPositionInMachine() {
        return rotorPositionInMachine;
    }
    //Because letters is private, we need a method to get it
    public int[] getLetters() {
        return letters;
    }

    //Because notchPosition is private, we need a method to get it
    public int getNotchPosition() {
        return notchPosition;
    }

    // Method to rotate forward the rotor by one to simulate the machine turning
    public void rotateForward() {
        int temp = letters[letters.length - 1]; // Save the last element to move it to the first position later

        //Iterate through letters array and shift each element to the right
        for (int i = letters.length - 1; i > 0; i--) {
            // Move the current element to the right, example : in [1,2,3,4], i = 1, so letters[3] = letters[2] so it becomes [1,2,3,3]
            letters[i] = letters[i - 1];
        }
        // Move the last element to the first position
        letters[0] = temp;
        // Update the notch position to the new position after rotation
        notchPosition = (notchPosition + 1) % letters.length; // Modulo to ensure it stays within the array bounds
    }

    // Method to rotate backward the rotor by one to simulate the machine turning
    public void rotateBackward() {
        int temp = letters[0]; // Save the first element to move it to the last position later

        //Iterate through letters array and shift each element to the left
        for (int i = 0; i < letters.length - 1; i++) {
            // Move the current element to the left, example : in [1,2,3,4], i = 1, so letters[0] = letters[1] so it becomes [2,3,4,4]
            letters[i] = letters[i + 1];
        }
        // Move the first element to the last position
        letters[letters.length - 1] = temp;
        // Update the notch position to the new position after rotation
        notchPosition = (notchPosition - 1 + letters.length) % letters.length; // Add letters.length to handle negative values
    }

    // Method to get the index of a letter in the rotor
    public int getLetterIndex (int letterPositionInAlphabet) {
        // Initialize the index to -1 to indicate that the letter is not found;
        int index = -1;
        for (int i = 0; i < letters.length; i++) {
            // Check if the letter is found in the rotor
            if (letters[i] == letterPositionInAlphabet) {
                // Update the index to the position of the letter in the rotor
                index = i;
                // Exit the loop once the letter is found
                break;
            }
        }
        return index;
    }
}