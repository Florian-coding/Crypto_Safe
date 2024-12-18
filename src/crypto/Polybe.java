package crypto;

import java.util.List;
import utils.Constants;
import utils.Verification;

public class Polybe {

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
            row =  Math.floorDiv(letterPositionPolybeSquare, Constants.POLYBE_SQUARE_SIDE_LENGTH) + 1;
            column = (letterPositionPolybeSquare % Constants.POLYBE_SQUARE_SIDE_LENGTH) + 1;
            result = rowFirst ? Integer.toString(row) + Integer.toString(column) : Integer.toString(column) + Integer.toString(row);
        }

        //System.out.println(polybeSquare + ": " + messageChar + " -> " + result);

        return result;
    }

    private static char getPolybeLetter(String polybeSquare, String coordinates, boolean rowFirst) {

        // Invert row and column if not rowFirst
        String rowString = (rowFirst ? coordinates.charAt(0) : coordinates.charAt(1)) + "";
        String columnString = (rowFirst ? coordinates.charAt(1) : coordinates.charAt(0)) + "";

        int row = Integer.parseInt(rowString);
        int column = Integer.parseInt(columnString);

        // Calculate string position frow row and column
        int position = ((row - 1) * Constants.POLYBE_SQUARE_SIDE_LENGTH) + (column - 1);
        
        boolean isAfterSlash = polybeSquare.indexOf("/") < position;

        if (isAfterSlash) position += 2;

        System.out.println(row + " " + column + " = " + position + " -> " + polybeSquare.charAt(position));

        return polybeSquare.charAt(position);
    }

    // encrypts a message using the polybe square
    public static String encrypt(String message, String polybeSquare, boolean rowFirst) throws Exception {
        
        // Checks if the polybe square is valid
        List<String> errorMessages = Verification.checkPolybeSquare(polybeSquare);

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

            // Handles v/w
            if (lowerPolybeSquare.contains("v/w")) {
                coordinatesString = switch (messageChar) {
                    case "w" -> getPolybeCoordinates(lowerPolybeSquare, "v/w", rowFirst);
                    case "v" -> getPolybeCoordinates(lowerPolybeSquare, "v/w", rowFirst);
                    default -> getPolybeCoordinates(lowerPolybeSquare, messageChar, rowFirst);
                };
            }

            // Handles i/j
            if (lowerPolybeSquare.contains("i/j")) {
                coordinatesString = switch (messageChar) {
                    case "i" -> getPolybeCoordinates(lowerPolybeSquare, "i/j", rowFirst);
                    case "j" -> getPolybeCoordinates(lowerPolybeSquare, "i/j", rowFirst);
                    default -> getPolybeCoordinates(lowerPolybeSquare, messageChar, rowFirst);
                };
            }

            // Adds the coordinate string to the encoded message
            encodedMessage += coordinatesString;
        }

        return encodedMessage;
    }

    public static String decrypt(String encryptedMessage, String polybeSquare, boolean rowFirst, boolean upperCase)  throws Exception {

        // Checks if the polybe square is valid
        List<String> errorMessages = Verification.checkPolybeSquare(polybeSquare);

        // Check if the encrypted polybe message is valid
        errorMessages.addAll(Verification.checkEncryptedPolybeMessage(encryptedMessage));

        // Throw errors if any
        if (!errorMessages.isEmpty()) {
            throw new Exception(String.join(" ", errorMessages));
        }

        String lowerPolybeSquare = polybeSquare.toLowerCase();

        // Decrypted message
        String decryptedMessage = "";
        
        // Message iterator
        int messageIterator;
        int stepsCount = encryptedMessage.length()/2;

        for (messageIterator = 0; messageIterator < stepsCount; messageIterator++) {
            int start = messageIterator * 2;
            int end = start + 2;
            String coordinatesString = encryptedMessage.substring(start, end);
            char letter = getPolybeLetter(lowerPolybeSquare, coordinatesString, rowFirst);
            decryptedMessage += letter;
        }

        if (upperCase) {
            decryptedMessage = decryptedMessage.toUpperCase();
        }

        return decryptedMessage;
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
        String ijInputEncryptedRow = "11242455"; // AIIZ
        String wvInputEncryptedColumn = "11252555"; // AVWZ

        // Expected
        String wvExpected = "32512323532553342341";
        String ijExpected = "425442315125241341";
        String ijExpectedDecryptedRow = "aiiz";
        String wvExpectedDecryptedColumn = "AVVZ";

        // Invalid length
        System.err.println( String.join(", ", Verification.checkPolybeSquare(invalidPolybeLength)));

        // Duplicate values
        System.err.println( String.join(", ", Verification.checkPolybeSquare(duplicatePolybeValue)));

        // Both
        System.err.println( String.join(", ", Verification.checkPolybeSquare(invalidLengthDuplicatesPolybe)));

        testEncrypt(wvPolybeSquare, wvInputString, wvExpected, rowFirst);
        testEncrypt(ijPolybeSquare, iAndJInputString, ijExpected, rowFirst);

        testDecrypt(wvPolybeSquare, wvInputEncryptedColumn, wvExpectedDecryptedColumn, false, true);
        testDecrypt(ijPolybeSquare, ijInputEncryptedRow, ijExpectedDecryptedRow, true, false);
    }

    // Method used for testing encrypt
    private static void testEncrypt (String polybeSquare, String inputString, String expectedString, boolean rowFirst) {
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

    // Method used for testing decrypt
    private static void testDecrypt(String polybeSquare, String inputString, String expectedString, boolean rowFirst, boolean upperCase) {
        String decryptedString;
        try {
            decryptedString = decrypt(inputString, polybeSquare, rowFirst, upperCase);

            System.out.println("Input: " + inputString);
            System.out.println("Decoded: " + decryptedString);

            if (decryptedString.equals(expectedString)) {
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