package crypto;

import data.Constants;

public class Polybe {

    // Encrypts using the polybe square
    public static String encrypt(String input, String polybeSquare, boolean rowFirst) {
        // Lowered strings
        String lowerInput = input.toLowerCase();
        String lowerPolybeSquare = polybeSquare.toLowerCase();

        // Return value
        String encrypted = "";

        // For iteration
        int iterator;
        String currentChar;

        // Used for coordinates
        String coordinatesString = "";

        for (iterator = 0; iterator < lowerInput.length(); iterator++) {

            // Get the nth character
            currentChar = lowerInput.charAt(iterator) + "";

            // Handles v/w
            if (lowerPolybeSquare.contains(Constants.POLYBE_VW)) {
                coordinatesString = switch (currentChar) {
                    case "w" ->
                        getCoordinatesFromLetter(lowerPolybeSquare, Constants.POLYBE_VW, rowFirst);
                    case "v" ->
                        getCoordinatesFromLetter(lowerPolybeSquare, Constants.POLYBE_VW, rowFirst);
                    default ->
                        getCoordinatesFromLetter(lowerPolybeSquare, currentChar, rowFirst);
                };
            }

            // Handles i/j
            if (lowerPolybeSquare.contains(Constants.POLYBE_IJ)) {
                coordinatesString = switch (currentChar) {
                    case "i" ->
                        getCoordinatesFromLetter(lowerPolybeSquare, Constants.POLYBE_IJ, rowFirst);
                    case "j" ->
                        getCoordinatesFromLetter(lowerPolybeSquare, Constants.POLYBE_IJ, rowFirst);
                    default ->
                        getCoordinatesFromLetter(lowerPolybeSquare, currentChar, rowFirst);
                };
            }

            // Adds the coordinate string to the encrypted
            encrypted += coordinatesString;
        }

        return encrypted;
    }

    // Decrypts en encrypted text using polybe square
    public static String decrypt(String encrypted, String polybeSquare, boolean rowFirst) {
        String lowerPolybeSquare = polybeSquare.toLowerCase();

        // Decrypted text
        String decrypted = "";

        // For iteration
        int iterator;
        int stepsCount = encrypted.length() / 2;

        for (iterator = 0; iterator < stepsCount; iterator++) {
            int start = iterator * 2;
            int end = start + 2;
            String coordinatesString = encrypted.substring(start, end);
            char letter = getLetterFromCoordinates(lowerPolybeSquare, coordinatesString, rowFirst);
            decrypted += letter;
        }

        return decrypted;
    }

    // Used to print a perfect polybe square
    public static void PrintSquare(String square) {
        int slashPosition = square.indexOf(Constants.POLYBE_SLASH);

        String s = "";

        for (int i = 0; i < Constants.POLYBE_SQUARE_NB_CELLS; i++) {
            int column = i % Constants.POLYBE_SQUARE_SIDE_LENGTH;
            
            // This is used to be able to put two "similar" letters ine one row
            String c = (i > slashPosition) ? square.charAt(i+2) + "" : square.charAt(i) + "";
            String c2 = i < Constants.POLYBE_SQUARE_NB_CELLS ? square.charAt(i+1) + "" : "";
            String c3 = i < Constants.POLYBE_SQUARE_NB_CELLS -1 ? square.charAt(i+2) + "" : "";

            String toAppend = c;

            // If the second character is Constants.POLYBE_SLASH, then we take the whole X/Y part
            if (Constants.POLYBE_SLASH.equals(c2)) {
                toAppend = c + c2 + c3;
            
            // This case is useful after the Constants.POLYBE_SLASH
            } else if (Constants.POLYBE_SLASH.equals(c)) {
                toAppend = c3;
            }

            // After every character, we put a tab
            s += toAppend + "\t";

            // If we are at the last column of the row, we add a new line
            if (column == Constants.POLYBE_SQUARE_SIDE_LENGTH - 1) s += "\n";
        }

        System.out.println(s);
    }
    
    // Used to get the 2 digits coordinates in the polybe square of a character
    private static String getCoordinatesFromLetter(String polybeSquare, String inputChar, boolean rowFirst) {
        int row;
        int column;
        int letterPositionPolybeSquare;
        String result = "";
        boolean isAfterSlash;

        // Get the position of the letter in the polybe square
        letterPositionPolybeSquare = polybeSquare.indexOf(inputChar);

        isAfterSlash = polybeSquare.indexOf(Constants.POLYBE_SLASH) < letterPositionPolybeSquare;

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

        return result;
    }

    // Used to letter from the coordinate in the polybe square
    private static char getLetterFromCoordinates(String polybeSquare, String coordinates, boolean rowFirst) {

        // Invert row and column if not rowFirst
        String rowString = (rowFirst ? coordinates.charAt(0) : coordinates.charAt(1)) + "";
        String columnString = (rowFirst ? coordinates.charAt(1) : coordinates.charAt(0)) + "";

        int row = Integer.parseInt(rowString);
        int column = Integer.parseInt(columnString);

        // Calculate string position frow row and column
        int position = ((row - 1) * Constants.POLYBE_SQUARE_SIDE_LENGTH) + (column - 1);

        boolean isAfterSlash = polybeSquare.indexOf(Constants.POLYBE_SLASH) < position;

        if (isAfterSlash) {
            position += 2;
        }

        return polybeSquare.charAt(position);
    }

    public static void main(String[] args) {
        PrintSquare("abcdefghijklmnopqrstuv/wxyz");
    }
}
