package crypto;

import utils.Constants;

public class Polybe {
    // encrypts a message using the polybe square
    public static String encrypt(String message, String polybeSquare, boolean rowFirst) {
        // Lowered strings
        String lowerMessage = message.toLowerCase();
        String lowerPolybeSquare = polybeSquare.toLowerCase();

        // Return value
        String encodedMessage = "";

        // Message iterator
        int messageIterator;

        // Used for coordinates
        String coordinatesString = "";

        for (messageIterator = 0; messageIterator < lowerMessage.length(); messageIterator++) {

            // Get the nth character of the message
            String messageChar = lowerMessage.charAt(messageIterator) + "";

            // Handles v/w
            if (lowerPolybeSquare.contains("v/w")) {
                coordinatesString = switch (messageChar) {
                    case "w" ->
                        getPolybeCoordinates(lowerPolybeSquare, "v/w", rowFirst);
                    case "v" ->
                        getPolybeCoordinates(lowerPolybeSquare, "v/w", rowFirst);
                    default ->
                        getPolybeCoordinates(lowerPolybeSquare, messageChar, rowFirst);
                };
            }

            // Handles i/j
            if (lowerPolybeSquare.contains("i/j")) {
                coordinatesString = switch (messageChar) {
                    case "i" ->
                        getPolybeCoordinates(lowerPolybeSquare, "i/j", rowFirst);
                    case "j" ->
                        getPolybeCoordinates(lowerPolybeSquare, "i/j", rowFirst);
                    default ->
                        getPolybeCoordinates(lowerPolybeSquare, messageChar, rowFirst);
                };
            }

            // Adds the coordinate string to the encoded message
            encodedMessage += coordinatesString;
        }

        return encodedMessage;
    }

    public static String decrypt(String encryptedMessage, String polybeSquare, boolean rowFirst) {
        String lowerPolybeSquare = polybeSquare.toLowerCase();

        // Decrypted message
        String decryptedMessage = "";

        // Message iterator
        int messageIterator;
        int stepsCount = encryptedMessage.length() / 2;

        for (messageIterator = 0; messageIterator < stepsCount; messageIterator++) {
            int start = messageIterator * 2;
            int end = start + 2;
            String coordinatesString = encryptedMessage.substring(start, end);
            char letter = getPolybeLetter(lowerPolybeSquare, coordinatesString, rowFirst);
            decryptedMessage += letter;
        }

        return decryptedMessage;
    }

    public static void DisplaySquare(String square) {
        int slashPosition = square.indexOf("/");

        String s = "";

        for (int i = 0; i < Constants.POLYBE_SQUARE_NB_CELLS; i++) {
            int column = i % Constants.POLYBE_SQUARE_SIDE_LENGTH;
            
            // This is used to be able to put two "similar" letters ine one row
            String c = (i > slashPosition) ? square.charAt(i+2) + "" : square.charAt(i) + "";
            String c2 = i < Constants.POLYBE_SQUARE_NB_CELLS ? square.charAt(i+1) + "" : "";
            String c3 = i < Constants.POLYBE_SQUARE_NB_CELLS -1 ? square.charAt(i+2) + "" : "";

            String toAppend = c;

            // If the second character is "/", then we take the whole X/Y part
            if ("/".equals(c2)) {
                toAppend = c + c2 + c3;
            
            // This case is useful after the "/"
            } else if ("/".equals(c)) {
                toAppend = c2;
            }

            // After every character, we put a tab
            s += toAppend + "\t";

            // If we are at the last column of the row, we add a new line
            if (column == Constants.POLYBE_SQUARE_SIDE_LENGTH - 1) s += "\n";
        }

        System.out.println(s);
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
            if (isAfterSlash) {
                letterPositionPolybeSquare -= 2;
            }

            // We add 1 here because the origin is 1 and not 0 in polybe square
            row = Math.floorDiv(letterPositionPolybeSquare, Constants.POLYBE_SQUARE_SIDE_LENGTH) + 1;
            column = (letterPositionPolybeSquare % Constants.POLYBE_SQUARE_SIDE_LENGTH) + 1;
            result = rowFirst ? Integer.toString(row) + Integer.toString(column)
                    : Integer.toString(column) + Integer.toString(row);
        }

        // System.out.println(polybeSquare + ": " + messageChar + " -> " + result);
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

        if (isAfterSlash) {
            position += 2;
        }

        System.out.println(row + " " + column + " = " + position + " -> " + polybeSquare.charAt(position));

        return polybeSquare.charAt(position);
    }
}
