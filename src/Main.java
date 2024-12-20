import cli.CommandLineInterface;
import crypto.AES;
import crypto.HMAC;
import crypto.MD5;
import crypto.NumberGenerator;
import crypto.Polybe;
import crypto.RC4;
import crypto.Rot;
import crypto.SHA256;
import crypto.Valdo;
import crypto.Vigenere;
import crypto.enigma.Enigma;
import crypto.enigma.Rotor;
import data.Constants;
import data.Help;
import data.Texts;
import java.util.Arrays;
import java.util.List;
import utils.FileManager;
import utils.Verification;


public class Main {
    public static String input;
    public static String output;

    public static void main(String[] args) {
        MainMenu();
    }

    public static void MainMenu() {
        CommandLineInterface.Clear();
        CommandLineInterface.DisplayInfo(Texts.APP_NAME_ASCII);
        CommandLineInterface.DisplayText(Texts.WELCOME);
        
        int selectedOption = CommandLineInterface.DisplayChoice("",Texts.MAIN_MENU);

        switch (selectedOption) {
            case 1 ->
                EncryptMenu();
            case 2 ->
                PreDecryptMenu();
            case 3 ->
                HashMenu();
            case 4 ->
                PseudoRandomMenu();
            case 5 ->
                //Todo: Implement steganography
                CommandLineInterface.DisplayInfo(Texts.EXIT);
            case 6 ->
                ViewFileMenu();
            case 7 ->
                HelpMenu();
            default -> {
                CommandLineInterface.DisplayInfo(Texts.EXIT);
            }
        }
    }

    public static void EncryptMenu() {
        CommandLineInterface.Clear();
        CommandLineInterface.DisplayInfo(Texts.ENCRYPT_MENU_CHOSEN);

        int selectedOption = CommandLineInterface.DisplayMenu(Texts.ENCRYPTION_METHODS);

        // This is used in case the user selects Rot(x), Vigenère, Polybe and Enigma
        boolean isLimitedInputAlgorithm = 1 <= selectedOption && selectedOption <= 5;

        // Inform the user that some characters won't be accepted
        if (isLimitedInputAlgorithm) {
            CommandLineInterface.DisplayInfo(Texts.ENCRYPTION_ALGORITHM_ALLOWED_CHARACTERS);
            CommandLineInterface.DisplayInfo(Texts.INPUT_WILL_BE_LOWERCASE);
        }

        // If user didn't choose to exit, prompt for password to encrypt
        if (selectedOption > 0) {
            input = CommandLineInterface.AskInput(Texts.INPUT_PASSWORD);
        } else {
            MainMenu();
            return;
        }

        // Check for specials characters and whitespaces, and turn upper to lower cases
        if (isLimitedInputAlgorithm) {
            // Converts to lower case
            input = input.toLowerCase();
            boolean isInvalid = !Verification.IsValidLowerAlphabet(input) || input.isEmpty() || input.isBlank();

            while (isInvalid) {
                CommandLineInterface.DisplayException(Texts.PASSWORD_ONLY_LETTERS);
                input = CommandLineInterface.AskInput(Texts.INPUT_PASSWORD).toLowerCase();
                isInvalid = !Verification.IsValidLowerAlphabet(input) || input.isEmpty() || input.isBlank();
            }
        }

        // Menu logic here
        switch (selectedOption) {
            case 1 -> {
                // Rot
                int rotations = CommandLineInterface.AskInteger(Texts.INPUT_ROT);
                output = Rot.encrypt(input, rotations);
                break;
            }
            case 2 -> {
                // Vigenère
                String key = CommandLineInterface.AskInput(Texts.INPUT_KEY);
                output = Vigenere.encrypt(input, key);
                break;
            }
            case 3 -> {
                // Polybe

                String[] polybeSquares = {Constants.POLYBE_SQUARE_VW, Constants.POLYBE_SQUARE_IJ , Texts.DEFINE_POLYBE_SQUARE};
                String selectedPolybeSquare;

                // Prompt for what square to use or create one
                int selectedSquare = CommandLineInterface.DisplayChoice(Texts.POLYBE_SQUARE_SELECTION, polybeSquares);

                // Custom square provided by user
                if (selectedSquare == 3) {
                    selectedPolybeSquare = CommandLineInterface.AskInput(Texts.POLYBE_INLINE_SQUARE);
                    List<String> errors = Verification.checkPolybeSquare(selectedPolybeSquare);

                    // Checks for error, if so, re prompts for a valid square
                    while (!errors.isEmpty()) {
                        CommandLineInterface.DisplayException(String.join(", ", errors));
                        selectedPolybeSquare = CommandLineInterface.AskInput(Texts.POLYBE_INLINE_SQUARE);
                        errors = Verification.checkPolybeSquare(selectedPolybeSquare);
                    }
                } else {
                    // Select valid square
                    selectedPolybeSquare = polybeSquares[selectedSquare - 1];
                }
                
                // Display the selected square 
                CommandLineInterface.DisplayText(Texts.POLYBE_SQUARE_DISPLAY);
                Polybe.PrintSquare(selectedPolybeSquare);

                // Ask for what axis to read the coordinates first
                int rowChoice = CommandLineInterface.DisplayChoice(Texts.AXIS_READ, Texts.XY_AXIS);
                boolean rowFirst = rowChoice == 1;

                output = Polybe.encrypt(input, selectedPolybeSquare, rowFirst);
                break;
            }
            case 4 -> {
                // Vadlo
                output = Valdo.encrypt(input);
            }
            case 5 -> {
                // Enigma
                Rotor rotorOne = new Rotor(Constants.ENIGMA_LETTERS1, 0, 12);
                Rotor rotorTwo = new Rotor(Constants.ENIGMA_LETTERS2, 1, 13);
                Rotor rotorThree = new Rotor(Constants.ENIGMA_LETTERS3, 2, 14);

                Enigma machine = new Enigma(rotorOne, rotorTwo, rotorThree);

                output = machine.encryptMessage(input);
                break;
            }
            case 6 -> {
                // RC4
                String key = CommandLineInterface.AskInput(Texts.INPUT_KEY);
                output = RC4.encrypt(input, key);
                break;
            }
            case 7 -> {
                // AES
                String key = CommandLineInterface.AskInput(Texts.INPUT_KEY);
                output = AES.encrypt(input, key);
                break;
            }
        }

        CommandLineInterface.DisplayText(Texts.ENCRYPTED_PASSWORD_RESULT + output);

        FileManager.writeToFile(output, Constants.CRYPTO_SAFE_FILE);
        CommandLineInterface.DisplayInfo(Texts.PASSWORD_STORED_IN + Constants.CRYPTO_SAFE_FILE);

        selectedOption = CommandLineInterface.DisplayChoice(Texts.REDO_OR_MENU, Texts.REDO_MENU_CHOICES);

        switch (selectedOption) {
            case 1 -> EncryptMenu();
        }

        MainMenu();
    }

    public static void PreDecryptMenu() {
        CommandLineInterface.Clear();
        CommandLineInterface.DisplayInfo(Texts.DECRYPT_MENU_CHOSEN);

        // Ask user if he wants to enter the password manually or choose one from the file
        int selectedOption = CommandLineInterface.DisplayMenu(Texts.PRE_DECRYPTION);

        switch (selectedOption) {
            case 1 -> {
                input = CommandLineInterface.AskInput(Texts.INPUT_PASSWORD_TO_DECRYPT);
                break;
            }
            case 2 -> {
                String[] encryptedMessages = FileManager.readFromFileAsArray(Constants.CRYPTO_SAFE_FILE);
                CommandLineInterface.DisplayText(Texts.INPUT_PASSWORD_TO_DECRYPT);
                selectedOption = CommandLineInterface.DisplayChoice("",encryptedMessages);
                input = encryptedMessages[selectedOption-1];
                break;
            }
            default -> MainMenu();
        }

        DecryptMenu();
    }

    public static void DecryptMenu(){
        CommandLineInterface.Clear();
        CommandLineInterface.DisplayInfo(Texts.USER_PROMPT_DECRYPT_METHOD);

        int selectedOption = CommandLineInterface.DisplayMenu(Texts.ENCRYPTION_METHODS);

        switch (selectedOption) {
            case 1 -> {
                // Rot
                int rotations = CommandLineInterface.AskInteger(Texts.INPUT_ROT);
                output = Rot.decrypt(input, rotations);
                break;
            }
            case 2 -> {
                // Vigenère
                String key = CommandLineInterface.AskInput(Texts.INPUT_KEY);
                output = Vigenere.decrypt(input, key);
                break;
            }
            case 3 -> {
                // Polybe

                String[] polybeSquares = {Constants.POLYBE_SQUARE_VW, Constants.POLYBE_SQUARE_IJ ,Texts. DEFINE_POLYBE_SQUARE};
                String selectedPolybeSquare;

                // Prompt for what square to use or create one
                int selectedSquare = CommandLineInterface.DisplayChoice(Texts.POLYBE_SQUARE_SELECTION, polybeSquares);

                // Custom square provided by user
                if (selectedSquare == 3) {
                    selectedPolybeSquare = CommandLineInterface.AskInput(Texts.POLYBE_INLINE_SQUARE);
                    List<String> errors = Verification.checkPolybeSquare(selectedPolybeSquare);

                    // Checks for error, if so, re prompts for a valid square
                    while (!errors.isEmpty()) {
                        CommandLineInterface.DisplayException(String.join(", ", errors));
                        selectedPolybeSquare = CommandLineInterface.AskInput(Texts.POLYBE_INLINE_SQUARE);
                        errors = Verification.checkPolybeSquare(selectedPolybeSquare);
                    }
                } else {
                    // Select valid square
                    selectedPolybeSquare = polybeSquares[selectedSquare - 1];
                }
                
                // Display the selected square 
                CommandLineInterface.DisplayText(Texts.POLYBE_SQUARE_DISPLAY);
                Polybe.PrintSquare(selectedPolybeSquare);

                // Ask for what axis to read the coordinates first
                int rowChoice = CommandLineInterface.DisplayChoice(Texts.AXIS_READ, Texts.XY_AXIS);
                boolean rowFirst = rowChoice == 1;

                output = Polybe.decrypt(input, selectedPolybeSquare, rowFirst);
                break;
            }
            case 4 -> {
                output = Valdo.decrypt(input);
            }
            case 5 -> {
                // Enigma
                Rotor rotorOne = new Rotor(Constants.ENIGMA_LETTERS1, 0, 12);
                Rotor rotorTwo = new Rotor(Constants.ENIGMA_LETTERS2, 1, 13);
                Rotor rotorThree = new Rotor(Constants.ENIGMA_LETTERS3, 2, 14);

                Enigma machine = new Enigma(rotorOne, rotorTwo, rotorThree);

                output = machine.decryptMessage(input);
                break;
            }
            case 6 -> {
                // RC4
                String key = CommandLineInterface.AskInput(Texts.INPUT_KEY);
                output = RC4.decrypt(input, key);
                break;
            }
            case 7 -> {
                // AES
                String key = CommandLineInterface.AskInput(Texts.INPUT_KEY);
                output = AES.decrypt(input, key);
                break;
            }
            default -> {
                MainMenu();
            }

        }
        CommandLineInterface.DisplayText(Texts.DECRYPTED_PASSWORD_RESULT + output);

        selectedOption = CommandLineInterface.DisplayChoice(Texts.REDO_OR_MENU, Texts.REDO_MENU_CHOICES);

        switch (selectedOption) {
            case 1 -> PreDecryptMenu();
            default -> MainMenu();
        }
    }

    public static void HashMenu() {
        CommandLineInterface.Clear();
        CommandLineInterface.DisplayInfo(Texts.HASH_MENU_CHOSEN);

        int selectedOption = CommandLineInterface.DisplayMenu(Texts.HASH_METHODS);

        // Prompt for a password to hash, if 0 was selected, then back to menu
        if (selectedOption > 0) {
            input = CommandLineInterface.AskInput(Texts.INPUT_PASSWORD);
        } else {
            MainMenu();
            return;
        }

        // HMAC needs a key in order to be hashed
        String key = "";
        if (selectedOption == 3) {
            while (key.isEmpty()) {
                key = CommandLineInterface.AskInput(Texts.INPUT_KEY);
                if (key.isEmpty()) {
                    CommandLineInterface.DisplayException(Texts.EMPTY_KEY);
                }
            }
        }

        // Hash using the algorithm selected by the user
        switch (selectedOption) {
            case 1 -> output = MD5.hash(input);
            case 2 -> output = SHA256.hash(input);
            case 3 -> output = HMAC.hash(input, key);
        }

        // Display result
        CommandLineInterface.DisplayText(Texts.COMPUTED_HASH + output);

        // Prompt user if he wants to retry or go back to menu
        selectedOption = CommandLineInterface.DisplayChoice(Texts.REDO_OR_MENU, Texts.REDO_MENU_CHOICES);

        // Get back to the top of this method
        switch (selectedOption) {
            case 1 -> HashMenu();
        }

        // Back to main menu
        MainMenu();
    }

    public static void PseudoRandomMenu() {
        CommandLineInterface.Clear();
        CommandLineInterface.DisplayInfo(Texts.RANDOM_MENU_CHOSEN);

        NumberGenerator generator = new NumberGenerator();
        CommandLineInterface.DisplayText(Texts.GENERATED_NUMBER + generator.generate());

        CommandLineInterface.DisplayText("");
        int selectedOption = CommandLineInterface.DisplayChoice(Texts.REDO_OR_MENU, Texts.REDO_MENU_CHOICES);

        switch (selectedOption) {
            case 1 -> PseudoRandomMenu();
        }

        MainMenu();
    }

    public static void ViewFileMenu() {
        CommandLineInterface.Clear();
        CommandLineInterface.DisplayInfo(Texts.VIEW_FILE_MENU_CHOSEN);
        CommandLineInterface.DisplayText(Texts.FILE_DISPLAY + Constants.CRYPTO_SAFE_FILE);
        CommandLineInterface.DisplayText(FileManager.readFromFile(Constants.CRYPTO_SAFE_FILE));

        int selectedOption = CommandLineInterface.DisplayChoice("",Texts.GO_TO_MENU);

        switch (selectedOption) {
            default -> {
                MainMenu();
                return;
            }
        }
    }

    public static void HelpMenu() {
        CommandLineInterface.Clear();
        CommandLineInterface.DisplayInfo(Texts.HELP_MENU_CHOSEN);

        // Concat encryptions and methods in one
        String[] algorithms = Arrays.copyOf(Texts.ENCRYPTION_METHODS, Texts.ENCRYPTION_METHODS.length + Texts.HASH_METHODS.length);
        System.arraycopy(Texts.HASH_METHODS, 0, algorithms, Texts.ENCRYPTION_METHODS.length, Texts.HASH_METHODS.length);

        int selectedAlgorithm = CommandLineInterface.DisplayMenu(algorithms);

        CommandLineInterface.Clear();

        if (selectedAlgorithm == 0) {
            MainMenu();
            return;
        }

        selectedAlgorithm -= 1;

        
        CommandLineInterface.DisplayInfo(algorithms[selectedAlgorithm]);

        String algoeirhmHelp = Help.HELP_SELECTION[selectedAlgorithm];

        CommandLineInterface.DisplayText(algoeirhmHelp);

        CommandLineInterface.DisplayText("");
        int selectedOption = CommandLineInterface.DisplayChoice(Texts.REDO_OR_MENU, Texts.REDO_MENU_CHOICES);

        switch (selectedOption) {
            case 1 -> {
                HelpMenu();
                return;
            }
            default -> MainMenu();
        }
    }
}
