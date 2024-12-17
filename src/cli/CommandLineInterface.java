import java.util.Scanner;

public class CommandLineInterface {

	// Texts
	private static final String ASK_SELECT_OPTION = "Select an option by pressing a key associated with the option.";
	private static final String RETURN_OPTION = "Return";
	private static final String ASK_VALID_INTEGER = "Please provide a valid integer.";
	private static final String ASK_VALID_RANGE = "Please provide a number in range of 0 - ";

	// Utilities
	private static final Scanner scan = new Scanner(System.in);

	// Terminal colors
	private static final String ANSI_RESET = "\u001B[0m";
	private static final String ANSI_RED = "\u001B[31m";
	private static final String ANSI_YELLOW = "\u001B[33m";

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
					DisplayException(ASK_VALID_RANGE + lastOptionKey);
				}
			} catch(Exception e) {
				invalidInput = true;
				DisplayException(ASK_VALID_INTEGER);
			}
		}

		return userSelectedOption;
	}

	// Display text
	public static  void DisplayException(String exception) {
		System.out.println(ANSI_RED + exception + ANSI_RESET);
	}
	public static  void DisplayText(String text) {
		System.out.println(ANSI_RESET + text + ANSI_RESET);
	}
	public static  void DisplayInfo(String text) {
		System.out.println(ANSI_YELLOW + text + ANSI_RESET);
	}

	// Main entry
	public static void main(String[] args) {
		String[] menu = {"ROT(X)","Vigenère","Polybe","Enigma","RC4","MD5","SHA256"};
		int inp = DisplayMenu(menu);
		DisplayInfo("Selected " + inp);
	}
}
