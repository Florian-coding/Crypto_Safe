package crypto;

import java.util.*;

public class GenerationNumber {
    public static void main(String[] args) {
        generate();
    }

    public static int generate4DigitNumber() {
        // Get the system time in milliseconds
        long currentTime = System.currentTimeMillis();

        // Generate a "random" number based on the current time
        // Using the time divided by a factor to get a 4-digit number
        int randomNumber = (int) currentTime * -1;

        return randomNumber;
    }

    public static void generate() {
        // Call function to generate a number based on time
        int randomNumber = generate4DigitNumber();

        System.out.println("Randomly generated 4-digit number = " + randomNumber);

        // Initialize an array to store each digit of the random number
        int[] seedArray = new int[4];

        // Extract each digit from the 4-digit number
        for (int i = 3; i >= 0; i--) {
            seedArray[i] = randomNumber % 10; // Extract the last digit
            randomNumber /= 10; // Remove the last digit
        }

        // Example LFSR-like calculation using the digits
        int firstNumber = seedArray[0];
        int secondNumber = seedArray[1];
        int thirdNumber = seedArray[2];
        int fourthNumber = seedArray[3];

        // Calculate LFSR value based on some arbitrary operation
        int lfsr = (firstNumber + secondNumber) * (thirdNumber + fourthNumber);

        System.out.println("LFSR = " + lfsr);
    }
}
