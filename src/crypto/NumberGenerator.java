package crypto;

public class NumberGenerator {
    private static int LFSR_MAX = 162;  // (9 + 9) * 9

    // Generate a float between 0 and 1
    public static float random() {

        // Get the current milliseconds by modulo 1000 to get range 0 - 999
        int currentTimeMS = (int) (System.currentTimeMillis() % 1000);

        // Initialize an array to store each digit of the random number
        int[] seedArray = new int[3];
        
        // Extract each digit from the 3-digit number
        for (int i = 2; i >= 0; i--) {
            seedArray[i] = currentTimeMS % 10; // Extract the last digit
            currentTimeMS /= 10; // Remove the last digit
        }
        
        // Get every digit
        int firstNumber = seedArray[0];
        int secondNumber = seedArray[1];
        int thirdNumber = seedArray[2];
        
        // Calculate LFSR value based on some arbitrary operation
        float lfsr = (firstNumber + secondNumber) * thirdNumber;

        // This converts to a float between 0 and 1
        lfsr = lfsr / LFSR_MAX;
        
        return lfsr;
    }

    // Generate an integer between [min] and [max] (both included)
    public static int randomInt(int min, int max) {
        float randomBetween0and1 = random();

        int finalRandom = (int)(randomBetween0and1 * (max - min) + min);

        return finalRandom;
    }

    // Basic method for hmac
    public static int generate() {
        return (int)(random() * LFSR_MAX);
    }
}
