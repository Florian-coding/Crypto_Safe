package cli;

import java.util.Scanner;
import data.Texts;

public class CommandLineInterface {
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
        displayItem(0, Texts.RETURN_OPTION);
        DisplayInfo(ANSI_YELLOW + Texts.ASK_SELECT_OPTION);

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
                    DisplayException(Texts.ASK_VALID_RANGE_0 + lastOptionKey);
                }
            } catch (Exception e) {
                invalidInput = true;
                DisplayException(Texts.ASK_VALID_INTEGER);
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
                    DisplayException(Texts.ASK_VALID_RANGE_1 + lastOptionKey);
                }
            } catch (Exception e) {
                invalidInput = true;
                DisplayException(Texts.ASK_VALID_INTEGER);
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
                DisplayException(Texts.ASK_VALID_INTEGER);
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
                DisplayException(Texts.ASK_VALID_INTEGER);
            }
        }

        return number;
    }
}
