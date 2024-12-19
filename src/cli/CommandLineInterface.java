package cli;

import java.util.Scanner;

public class CommandLineInterface {

    // Texts
    private static final String ASK_SELECT_OPTION = "Veuillez selectionner une option en entrant sa touche associé.";
    private static final String RETURN_OPTION = "Retour";
    private static final String ASK_VALID_INTEGER = "Veuillez saisir un entier valide.";
    private static final String ASK_VALID_RANGE_0 = "Veuillez saisir un entier compris entre 0 et ";
    private static final String ASK_VALID_RANGE_1 = "Veuillez saisir un entier compris entre 1 et ";

    // Ascii
    private static final String VERTICAL_ASCII = "│";
    private static final String HORIZONTAL_ASCII = "─";
    private static final String TOP_LEFT_ASCII = "┌";
    private static final String TOP_RIGHT_ASCII = "┐";
    private static final String BOTTOM_RIGHT_ASCII = "┘";
    private static final String BOTTOM_LEFT_ASCII = "└";

    // Utilities
    private static final Scanner scan = new Scanner(System.in);

    // Terminal colors
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_YELLOW = "\u001B[33m";

    // Terminal actions
    private static final String ANSI_START_LINE = "\033[H\\";
    private static final String ANSI_CLEAR = "\033[2J";

    private static void displayItem(int itemNumber, String itemMenu) {
        String itemKey = "[" + itemNumber + "]";
        System.out.println(ANSI_YELLOW + itemKey + ANSI_RESET + " " + itemMenu);
    }

    // Display menu
    public static int DisplayMenu(String[] items) {
        int itemIterator;
        int userSelectedOption = -1;
        String userInput;
        int lastOptionKey = items.length;
        boolean invalidInput = true;

        // Display menu options
        for (itemIterator = 0; itemIterator < items.length; itemIterator++) {
            displayItem(itemIterator + 1, items[itemIterator]);
        }

        // Display return option
        displayItem(0, RETURN_OPTION);
        DisplayInfo(ANSI_YELLOW + ASK_SELECT_OPTION);

        // Loop until user input is valid
        while (invalidInput) {
            // Get user input
            userInput = scan.nextLine();

            try {
                userSelectedOption = Integer.parseInt(userInput);
                if (0 <= userSelectedOption && userSelectedOption <= lastOptionKey) {
                    invalidInput = false;
                    break;
                } else {
                    DisplayException(ASK_VALID_RANGE_0 + lastOptionKey);
                }
            } catch (Exception e) {
                invalidInput = true;
                DisplayException(ASK_VALID_INTEGER);
            }
        }

        return userSelectedOption;
    }

    public static int DisplayChoice(String question, String[] choices) {
        int itemIterator;
        int userSelectedOption = -1;
        String userInput;
        int lastOptionKey = choices.length;
        boolean invalidInput = true;

        // Display question
        DisplayInfo(question);

        // Display choices
        for (itemIterator = 0; itemIterator < choices.length; itemIterator++) {
            displayItem(itemIterator + 1, choices[itemIterator]);
        }

        // Loop until user input is valid
        while (invalidInput) {
            // Get user input
            userInput = scan.nextLine();

            try {
                userSelectedOption = Integer.parseInt(userInput);
                if (0 < userSelectedOption && userSelectedOption <= lastOptionKey) {
                    invalidInput = false;
                    break;
                } else {
                    DisplayException(ASK_VALID_RANGE_1 + lastOptionKey);
                }
            } catch (Exception e) {
                invalidInput = true;
                DisplayException(ASK_VALID_INTEGER);
            }
        }

        return userSelectedOption;
    }

    // Display red text
    public static void DisplayException(String exception) {
        System.out.println(ANSI_RED + exception + ANSI_RESET);
    }

    // Display white text
    public static void DisplayText(String text) {
        System.out.println(ANSI_RESET + text + ANSI_RESET);
    }

    // Display yellow text
    public static void DisplayInfo(String text) {
        System.out.println(ANSI_YELLOW + text + ANSI_RESET);
    }

    // Clears the console
    public static void Clear() {
        System.out.println(ANSI_START_LINE + ANSI_CLEAR);
    }

    // Asks something and wait for input
    public static String AskInput(String message) {
        System.out.print(message + " : ");
        return scan.nextLine();
    }

    public static int AskPostiveInteger(String message) {
        System.out.print(message + " : ");
        int number = -1;
        String input;
        boolean isInvalid = true;

        while (isInvalid) {
            input = scan.nextLine();

            try {
                number = Integer.parseInt(input);
                if (number < 0) isInvalid = false;
                break;
            } catch (Exception e) {
                isInvalid = true;
                DisplayException(ASK_VALID_INTEGER);
            }
        }

        return number;
    }

    public static int AskInteger(String message) {
        System.out.print(message + " : ");
        int number = -1;
        String input;
        boolean isInvalid = true;

        while (isInvalid) {
            input = scan.nextLine();

            try {
                number = Integer.parseInt(input);
                break;
            } catch (Exception e) {
                isInvalid = true;
                DisplayException(ASK_VALID_INTEGER);
            }
        }

        return number;
    }

    // Display box
    /*
     * public static String DisplayBox(int rows, int columns, List<String> messages)
     * {
     * int y;
     * int x;
     * int totalWidth = columns + 2;
     * int totalHeight = rows + 2;
     * boolean left, right, top, bottom;
     * 
     * StringBuilder box = new StringBuilder();
     * 
     * for (y = 0; y < totalHeight; y++) {
     * for (x = 0; x < totalWidth; x++) {
     * left = (x == 0);
     * right = (x == totalWidth - 1);
     * top = (y == 0);
     * bottom = (y == totalHeight - 1);
     * 
     * if (left && top) {
     * box.append(TOP_LEFT_ASCII);
     * } else if (right && top) {
     * box.append(TOP_RIGHT_ASCII);
     * } else if (left && bottom) {
     * box.append(BOTTOM_LEFT_ASCII);
     * } else if (right && bottom) {
     * box.append(BOTTOM_RIGHT_ASCII);
     * } else if ((left || right) && !(top || bottom)) {
     * box.append(VERTICAL_ASCII);
     * } else if ((top || bottom) && !(left || right)) {
     * box.append(HORIZONTAL_ASCII);
     * } else {
     * box.append(" ");
     * }
     * }
     * box.append("\n");
     * }
     * 
     * return box.toString();
     * }
     */
    // Main entry
    public static void main(String[] args) {
        String[] menu = { "ROT(X)", "Vigenère", "Polybe", "Enigma", "RC4", "MD5", "SHA256" };
        int input = DisplayMenu(menu);
        DisplayInfo("Selected " + input);
    }
}
