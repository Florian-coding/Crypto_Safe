package crypto;

import java.util.ArrayList;
import java.util.List;

public class Polybe {
    // Constants
    public static final int SQUARE_SIDE_LENGTH = 5;
    public static final int SQUARE_NB_CELLS = 25;
    
    // Error messages
    public static final String INCORRECT_SQUARE_LENGTH = "Please provide a 5x5 polybe square";
    public static final String DUPLICATES_SQUARE_VALUES = "Please provide unique values in the polybe square";

    // Test if polybe square is valid (25 unique values), returns a list of errors if any
    public static List<String> checkPolybeSquare(String polybeSquare) {
        List<String> errors = new ArrayList<>();
        int iterator;
        String uniqueLetters = "";
        String filteredSquare = polybeSquare.replace("/", "");
        
        // Check length, by removing the / and subtracting by a letter (instead of counting v/w  we count v  )
        if (filteredSquare.length() - 1 != SQUARE_NB_CELLS) {
            errors.add(INCORRECT_SQUARE_LENGTH);
        }

        // Check for duplicates
        for (iterator = 0; iterator < filteredSquare.length(); iterator++) {

            // Get the character
            char squareCell = filteredSquare.charAt(iterator);

            // If it is already in the unique letters String, we stop and add error
            if (uniqueLetters.indexOf(squareCell) != -1) {
                errors.add(DUPLICATES_SQUARE_VALUES);
                break;
            } else {
                uniqueLetters += squareCell;
            }
        }

        return errors;
    }

    // Used to get the 2 digits coordinates in the polybe square of a character
    private static String getPolybeCoordinates(String polybeSquare, String messageChar, boolean rowFirst) {
        int row;
        int column;
        int letterPositionPolybeSquare;
        String result = "";
        boolean isAfterSlash;

        // Get the position of the letter in the polybe square
        letterPositionPolybeSquare = polybeSquare.indexOf(messageChar);

        isAfterSlash = polybeSquare.indexOf("/") < letterPositionPolybeSquare;

        // If it exists in the polybe square
        if (letterPositionPolybeSquare != -1) {

            // Remove 2 if it's after /
            if (isAfterSlash) letterPositionPolybeSquare -= 2;

            // We add 1 here because the origin is 1 and not 0 in polybe square
            row =  Math.floorDiv(letterPositionPolybeSquare,SQUARE_SIDE_LENGTH) + 1;
            column = (letterPositionPolybeSquare % SQUARE_SIDE_LENGTH) + 1;
            result = rowFirst ? Integer.toString(row) + Integer.toString(column) : Integer.toString(column) + Integer.toString(row);
        }

        //System.out.println(polybeSquare + ": " + messageChar + " -> " + result);

        return result;
    }

    // encrypts a message using the polybe square
    public static String encrypt(String message, String polybeSquare, boolean rowFirst) throws Exception {
        
        // Checks if the polybe square is valid
        List<String> errorMessages = checkPolybeSquare(polybeSquare);

        // Lowered strings
        String lowerMessage = message.toLowerCase();
        String lowerPolybeSquare = polybeSquare.toLowerCase();

        // Return value
        String encodedMessage = "";
        
        // Message iterator
        int messageIterator;

        // Used for coordinates
        String coordinatesString = "";

        // Throw errors if any
        if (!errorMessages.isEmpty()) {
            throw new Exception(String.join(" ", errorMessages));
        }

        for (messageIterator = 0; messageIterator < lowerMessage.length(); messageIterator++) {

            // Get the nth character of the message
            String messageChar = lowerMessage.charAt(messageIterator) + "";

            // Adds the coordinate string to the encoded message, handles w, v, i and j

            // Handles v/w
            if (lowerPolybeSquare.contains("v/w")) {
                coordinatesString = switch (messageChar) {
                    case "w" -> getPolybeCoordinates(lowerPolybeSquare, "v/w", rowFirst);
                    case "v" -> getPolybeCoordinates(lowerPolybeSquare, "v/w", rowFirst);
                    default -> getPolybeCoordinates(lowerPolybeSquare, messageChar, rowFirst);
                };
            }

            if (lowerPolybeSquare.contains("i/j")) {
                coordinatesString = switch (messageChar) {
                    case "i" -> getPolybeCoordinates(lowerPolybeSquare, "i/j", rowFirst);
                    case "j" -> getPolybeCoordinates(lowerPolybeSquare, "i/j", rowFirst);
                    default -> getPolybeCoordinates(lowerPolybeSquare, messageChar, rowFirst);
                };
            }

            encodedMessage += coordinatesString;
        }

        return encodedMessage;
    }

    // Used for tests
    public static void main(String[] args) {

        // Invalid squares
        String invalidPolybeLength = "abcdefghijklmnopqrstuvwxy";
        String duplicatePolybeValue = "abcdefghijklmnopqrstuvxyzz";
        String invalidLengthDuplicatesPolybe = "aabcdefghijklmnopqrstuvxyzz";

        // Valid squares
        String wvPolybeSquare = "abcdefghijklmnopqrstuv/wxyz";
        String ijPolybeSquare = "abcdefghi/jklmnopqrstuvwxyz";

        // Input
        boolean rowFirst = false;
        String wvInputString = "hello world";
        String iAndJInputString = "juice wrld";

        // Expected
        String wvExpected = "32512323532553342341";
        String ijExpected = "425442315125241341";

        // Invalid length
        System.err.println( String.join(", ", checkPolybeSquare(invalidPolybeLength)));

        // Duplicate values
        System.err.println( String.join(", ", checkPolybeSquare(duplicatePolybeValue)));

        // Both
        System.err.println( String.join(", ", checkPolybeSquare(invalidLengthDuplicatesPolybe)));


        test(wvPolybeSquare, wvInputString, wvExpected, rowFirst);
        test(ijPolybeSquare, iAndJInputString, ijExpected, rowFirst);
    }

    private static void test(String polybeSquare, String inputString, String expectedString, boolean rowFirst) {
        String encodedString;
        try {
            encodedString = encrypt(inputString, polybeSquare, rowFirst);

            System.out.println("Input: " + inputString);
            System.out.println("Encoded: " + encodedString);

            if (encodedString.equals(expectedString)) {
                System.out.println("As expected");
            } else {
                System.out.println("Not expected");
            }
        } catch (Exception e) {
            // Shouldn't happen.
            System.err.println(e.getMessage());
        }
    }
}