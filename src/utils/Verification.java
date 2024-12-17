package utils;


public class Verification {
    public static boolean isValidPassword(String input) {
        // Loop through each character in the string
        for (int i = 0; i < input.length(); i++) {
            char currentChar = input.charAt(i);

            // Check if the character is NOT a lowercase letter
            // This includes uppercase letters, spaces, digits, or special characters
            if (currentChar < 'a' || currentChar > 'z') {
                return false; // Invalid character found
            }
        }
        return true; // Valid string if all characters are lowercase letters
    }

    public static void main(String[] args) {

    }

}