import cli.CommandLineInterface;
import crypto.AES;
import crypto.HMAC;
import crypto.MD5;
import crypto.Polybe;
import crypto.RC4;
import crypto.Rot;
import crypto.SHA256;
import crypto.Vigenere;
import data.Texts;
import utils.FileManager;
import utils.Verification;

import java.util.List;

public class Main {
    public static String input;
    public static String output;

    public static void main(String[] args) {
        System.out.println("test");
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
        boolean isLimitedInputAlgorithm = 1 <= selectedOption && selectedOption <= 4;

        // Inform the user that some characters won't be accepted
        if (isLimitedInputAlgorithm) {
            CommandLineInterface.DisplayInfo("L'algorithme que vous avez choisie n'accepte pas les espaces, les chiffres et les caractères spéciaux.");
            CommandLineInterface.DisplayInfo("Les majuscules seronts traités comme des minuscules et les caractères non accepté seront rétirés.");
        }

        // If user didn't choose to exit, prompt for password to encrypt
        if (selectedOption > 0) {
            input = CommandLineInterface.AskInput("Saisir le mot de passe à chiffrer");
        } else {
            MainMenu();
            return;
        }

        // Remove all specials characters and whitespaces, and turn upper to lower cases
        if (isLimitedInputAlgorithm) {
            String oldInput = input;
            input = input.toLowerCase().replaceAll("[^a-z]+", "");

            // Display that the text changed
            if (!oldInput.equals(input)) CommandLineInterface.DisplayInfo("Voici la saisie final: " + input);
        }

        // Menu logic here
        switch (selectedOption) {
            case 1 -> {
                // Rot
                int rotations = CommandLineInterface.AskInteger("Saisir un décalage");
                output = Rot.encrypt(input, rotations);
                break;
            }
            case 2 -> {
                // Vigenère
                String key = CommandLineInterface.AskInput("Saisir une clé");
                output = Vigenere.encrypt(input, key);
                break;
            }
            case 3 -> {
                // Polybe
                String[] xy = {"Abscisse", "Ordonné"};

                String[] polybeSquares = {"abcdefghijklmnopqrstuv/wxyz", "abcdefghi/jklmnopqrstuvwxyz", "Définir un autre carré de polybe"};
                String selectedPolybeSquare;

                // Prompt for what square to use or create one
                int selectedSquare = CommandLineInterface.DisplayChoice("Quelle carré de polybe souhaitez vous utiliser ?", polybeSquares);

                // Custom square provided by user
                if (selectedSquare == 3) {
                    selectedPolybeSquare = CommandLineInterface.AskInput("Saisir un carré de polybe sur une ligne");
                    List<String> errors = Verification.checkPolybeSquare(selectedPolybeSquare);

                    // Checks for error, if so, re prompts for a valid square
                    while (!errors.isEmpty()) {
                        CommandLineInterface.DisplayException(String.join(", ", errors));
                        selectedPolybeSquare = CommandLineInterface.AskInput("Saisir un carré de polybe sur une ligne");
                        errors = Verification.checkPolybeSquare(selectedPolybeSquare);
                    }
                } else {
                    // Select valid square
                    selectedPolybeSquare = polybeSquares[selectedSquare - 1];
                }
                
                // Display the selected square 
                CommandLineInterface.DisplayText("Voici votre carré");
                Polybe.PrintSquare(selectedPolybeSquare);

                // Ask for what axis to read the coordinates first
                int rowChoice = CommandLineInterface.DisplayChoice("Lire par ordoonée ou abscisse ?", xy);
                boolean rowFirst = rowChoice == 1;

                output = Polybe.encrypt(input, selectedPolybeSquare, rowFirst);
                break;
            }
            case 4 -> {
                // Enigma
                
                break;
            }
            case 5 -> {
                // RC4
                String key = CommandLineInterface.AskInput("Saisir une clé");
                output = RC4.encrypt(input, key);
                break;
            }
            case 6 -> {
                // AES
                String key = CommandLineInterface.AskInput("Saisir une clé");
                output = AES.encrypt(input, key);
                break;
            }
        }

        CommandLineInterface.DisplayText("Mot de passe chiffré: " + output);

        FileManager.writeToFile(output, "crypto_safe.txt");
        CommandLineInterface.DisplayInfo("Le mot de passe chiffré a été stocké dans le fichier crypto_safe.txt");

        selectedOption = CommandLineInterface.DisplayChoice(Texts.REDO_OR_MENU, Texts.REDO_MENU_CHOICES);

        switch (selectedOption) {
            case 1 -> EncryptMenu();
        }

        MainMenu();
    }

    public static void PreDecryptMenu() {
        CommandLineInterface.Clear();
        CommandLineInterface.DisplayInfo(Texts.DECRYPT_MENU_CHOSEN);

        int selectedOption = CommandLineInterface.DisplayMenu(Texts.PRE_DECRYPTION);

        switch (selectedOption) {
            case 1 -> {
                input = CommandLineInterface.AskInput("Saisir le mot de passe à déchiffrer");
                break;
            }
            case 2 -> {
                String[] encryptedMessages = FileManager.readFromFileAsArray("crypto_safe.txt");
                CommandLineInterface.DisplayText("Choisissez un mot de passe à déchiffrer: ");
                selectedOption = CommandLineInterface.DisplayChoice("",encryptedMessages);
                input = encryptedMessages[selectedOption-1];
                break;
            }
            default -> MainMenu();
        }

        DecryptMenu(input);


    }

    public static void DecryptMenu(String encryptedText){
        CommandLineInterface.Clear();
        CommandLineInterface.DisplayInfo("Veuillez choisir la méthode de déchiffrage :");

        int selectedOption = CommandLineInterface.DisplayMenu(Texts.ENCRYPTION_METHODS);

        switch (selectedOption) {
            case 1 -> {
                // Rot
                int rotations = CommandLineInterface.AskInteger("Saisir un décalage");
                output = Rot.decrypt(encryptedText, rotations);
                break;
            }
            case 2 -> {
                // Vigenère
                String key = CommandLineInterface.AskInput("Saisir une clé");
                output = Vigenere.decrypt(encryptedText, key);
                break;
            }
            case 3 -> {
                // Polybe
                String[] xy = {"Abscisse", "Ordonné"};

                String[] polybeSquares = {"abcdefghijklmnopqrstuv/wxyz", "abcdefghi/jklmnopqrstuvwxyz", "Définir un autre carré de polybe"};
                String selectedPolybeSquare;

                // Prompt for what square to use or create one
                int selectedSquare = CommandLineInterface.DisplayChoice("Quelle carré de polybe souhaitez vous utiliser ?", polybeSquares);

                // Custom square provided by user
                if (selectedSquare == 3) {
                    selectedPolybeSquare = CommandLineInterface.AskInput("Saisir un carré de polybe sur une ligne");
                    List<String> errors = Verification.checkPolybeSquare(selectedPolybeSquare);

                    // Checks for error, if so, re prompts for a valid square
                    while (!errors.isEmpty()) {
                        CommandLineInterface.DisplayException(String.join(", ", errors));
                        selectedPolybeSquare = CommandLineInterface.AskInput("Saisir un carré de polybe sur une ligne");
                        errors = Verification.checkPolybeSquare(selectedPolybeSquare);
                    }
                } else {
                    // Select valid square
                    selectedPolybeSquare = polybeSquares[selectedSquare - 1];
                }

                // Display the selected square
                CommandLineInterface.DisplayText("Voici votre carré");
                Polybe.PrintSquare(selectedPolybeSquare);

                // Ask for what axis to read the coordinates first
                int rowChoice = CommandLineInterface.DisplayChoice("Lire par ordoonée ou abscisse ?", xy);
                boolean rowFirst = rowChoice == 1;

                output = Polybe.decrypt(encryptedText, selectedPolybeSquare, rowFirst);
                break;
            }
            default -> {
                MainMenu();
            }

        }
        CommandLineInterface.DisplayText("Mot de passe déchiffré: " + output);

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
            input = CommandLineInterface.AskInput("Saisir le mot de passe à hasher");
        } else {
            MainMenu();
            return;
        }

        // HMAC needs a key in order to be hashed
        String key = "";
        if (selectedOption == 3) {
            while (key.isEmpty()) {
                key = CommandLineInterface.AskInput("Saisir une clé de hachage");
                if (key.isEmpty()) {
                    CommandLineInterface.DisplayException("La clé ne peut être vide !");
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
        CommandLineInterface.DisplayText("Hash calculé: " + output);

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
    }

    public static void ViewFileMenu() {
        CommandLineInterface.Clear();
        CommandLineInterface.DisplayInfo(Texts.VIEW_FILE_MENU_CHOSEN);
        CommandLineInterface.DisplayText("Le contenu du fichier crypto_safe.txt est le suivant:");
        CommandLineInterface.DisplayText(FileManager.readFromFile("crypto_safe.txt"));

        MainMenu();
    }
}
