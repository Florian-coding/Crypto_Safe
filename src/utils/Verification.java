package utils;

import java.util.ArrayList;
import java.util.List;
import data.Constants;

public class Verification {
    
    // Error messages
    public static final String INCORRECT_POLYBE_SQUARE_LENGTH = "Veuillez saisir un carré de Polybe 5x5";
    public static final String DUPLICATES_POLYBE_SQUARE_VALUES = "Veuillez saisir des valeurs unique dans votre carré de Polybe";
    public static final String INCORRECT_POLYBE_PASSWORD = "Veuillez saisir un mot de passe encrypté de Polybe valide";
    public static final String INCORRECT_POLYBE_SQUARE_VALUES_IJ_VW = "Veuillez saisir un carré de Polybe comportant uniquement la paire v/w ou i/j";


    // Test if polybe square is valid (25 unique values), returns a list of errors if any
    public static List<String> checkPolybeSquare(String polybeSquare) {
        List<String> errors = new ArrayList<>();
        int iterator;
        String uniqueLetters = "";

        // Check if the square has i/j or v/w
        // If it has both pairs or doesn't have any pair, add error
        // Simpler condition to (HAS_VW && HAS_IJ) || (!HAS_VW && !HAS_IJ) = HAS_VW = HAS_IJ
        if ((polybeSquare.indexOf(Constants.POLYBE_VW) == -1) == (polybeSquare.indexOf(Constants.POLYBE_IJ) == -1)) {
            errors.add(INCORRECT_POLYBE_SQUARE_VALUES_IJ_VW);
        }

        // Remove slash / for count and iterating
        String filteredSquare = polybeSquare.replace(Constants.POLYBE_SLASH, "");
        
        // Check length, by removing the / and subtracting by a letter (instead of counting v/w  we count v  )
        if (filteredSquare.length() - 1 != Constants.POLYBE_SQUARE_NB_CELLS) {
            errors.add(INCORRECT_POLYBE_SQUARE_LENGTH);
        }

        // Check for duplicates
        for (iterator = 0; iterator < filteredSquare.length(); iterator++) {

            // Get the character
            char squareCell = filteredSquare.charAt(iterator);

            // If it is already in the unique letters String, we stop and add error
            if (uniqueLetters.indexOf(squareCell) != -1) {
                errors.add(DUPLICATES_POLYBE_SQUARE_VALUES);
                break;
            } else {
                uniqueLetters += squareCell;
            }
        }

        return errors;
    }

    // Validates if it's a valid encrypted polybe text (pair length, digits only)
    public static List<String> checkEncryptedPolybeMessage(String encrypted) {
        boolean error = false;
        int iterator;
        List<String> errors = new ArrayList<>();

        // Check it has no single digit outside the pairs
        error = (encrypted.length() % 2 != 0);

        for (iterator = 0; iterator < encrypted.length(); iterator++) {
            String c = encrypted.charAt(iterator) + "";
            try {
                Integer.parseInt(c);
            } catch (NumberFormatException e) {
                error = true;
                break;
            }
        }

        if (error) errors.add(INCORRECT_POLYBE_PASSWORD);
        
        return errors;
    }
    
    // Checks if provided string only has lower alphabetical character
    public static boolean IsValidLowerAlphabet(String input) {
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
}