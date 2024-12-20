package crypto;

public class RC4 {
    public static String encrypt(String input, String key) {
        // Convert strings into arrays of integers
        int[] inputClearArray = input.chars().toArray();
        int[] keys = key.chars().toArray();

        StringBuilder encrypted = new StringBuilder();

        // Initialize two integer arrays with a length of 256
        int[] S = new int[256];
        int[] T = new int[256];

        // Initialize T and S
        for (int i = 0; i < 256; i++) {
            // In S, assign values from 0 to 255
            S[i] = i;
            // In T, repeat the key until the maximum length of the array (256)
            T[i] = keys[i % keys.length];
        }

        int j = 0;
        // Loop from 0 to 255 to traverse the S array
        for (int k = 0; k < 256; k++) {
            // Add the value of j, a value from the S array, and a value from the T array
            // Then perform a modulo operation to ensure we stay within array bounds
            j = (j + S[k] + T[k]) % 256;
            // Call the swap function to shuffle positions in the S array
            swap(S, k, j);
        }

        int i = 0;
        j = 0;

        // Loop from 0 to the length of the cleartext input array
        for (int l = 0; l < inputClearArray.length; l++) {
            // Add 1 to the value of i
            // Perform a modulo operation to ensure we stay within array bounds
            i = (i + 1) % 256;
            // Add the value of j and a value from the S array
            // Perform a modulo operation to ensure we stay within array bounds
            j = (j + S[i]) % 256;
            // Call the swap function to shuffle positions in the S array
            swap(S, i, j);

            // Generate a keystream
            // Add two values from the S array
            int keyStream = S[(S[i] + S[j]) % 256];

            // Add a space separator for each number (except the first one)
            if (l > 0) {
                encrypted.append(" ");
            }

            // Perform XOR (^) between a value from the cleartext input array
            // and a value from the generated keystream
            encrypted.append(inputClearArray[l] ^ keyStream);
        }

        return encrypted.toString();
    }

    public static String decrypt(String encrypted, String key) {
        // Split encrypted text as array of strings by space
        String[] encryptedNumbers = encrypted.split(" ");
        // Initialize an integer array with a length equal to the length of the
        // encryptedNumbers array
        int[] encryptedArray = new int[encryptedNumbers.length];

        // Convert the key into an array of integers
        int[] keys = key.chars().toArray();

        StringBuilder decrypted = new StringBuilder();

        // Initialize two integer arrays with a length of 256
        int[] S = new int[256];
        int[] T = new int[256];

        for (int m = 0; m < encryptedNumbers.length; m++) {
            // Convert the value of the array (string) to an int and store it in the integer
            // array
            encryptedArray[m] = Integer.parseInt(encryptedNumbers[m]);
        }

        // Initialize T and S
        for (int i = 0; i < 256; i++) {
            // In S, assign values from 0 to 255
            S[i] = i;
            // In T, repeat the key until the maximum length of the array (256)
            T[i] = keys[i % keys.length];
        }

        int j = 0;
        // Loop from 0 to 255 to traverse the S array
        for (int k = 0; k < 256; k++) {
            // Add the value of j, a value from the S array, and a value from the T array
            // Then perform a modulo operation to ensure we stay within array bounds
            j = (j + S[k] + T[k]) % 256;
            // Call the swap function to shuffle positions in the S array
            swap(S, k, j);
        }

        int i = 0;
        j = 0;

        // Decrypt the encrypted text
        for (int l = 0; l < encryptedArray.length; l++) {
            // Add 1 to the value of i
            i = (i + 1) % 256;
            // Add the value of j and a value from the S array
            // Perform a modulo operation to ensure we stay within array bounds
            j = (j + S[i]) % 256;
            // Call the swap function to shuffle positions in the S array
            swap(S, i, j);

            // Generate a keystream
            // Add two values from the S array
            int keyStream = S[(S[i] + S[j]) % 256];

            // Perform XOR (^) between a value from the encrypted array
            // and a value from the generated keystream
            decrypted.append((char) (encryptedArray[l] ^ keyStream));
        }

        return decrypted.toString();
    }

    // Function that takes an array and two integers as parameters
    // to swap the elements in the array
    private static void swap(int[] array, int a, int b) {
        int temp = array[a];
        // Swap the positions of a and b
        array[a] = array[b];
        array[b] = temp;
    }
}
