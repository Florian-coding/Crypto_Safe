package data;

public class Texts {
    // --- MENU -- //
    public static final String APP_NAME = "CRYPTO SAFE";
    public static final String WELCOME = "Bienvenue sur notre gestionnaire de mot de passe exceptionnel !";
    public static final String[] MAIN_MENU = { "Chiffrer un mot de passe", "Déchiffrer un mot de passe", "Hacher un mot de passe", "Générer un nombre entier pseudo-aléatoire", "Cacher un message en stéganographie","Voir mes mots de passe","Aide", "Quitter" };
    public static final String[] ENCRYPTION_METHODS = { "ROT(X)", "Vigenère", "Polybe", "Valdo", "Enigma", "RC4", "AES" };
    public static final String[] PRE_DECRYPTION = {"Entrer un mot de passe à déchiffrer manuellement", "Déchiffrer un mot de passe à partir d'un fichier"};
    public static final String[] HASH_METHODS = { "MD5", "SHA-256", "HMAC" };
    public static final String[] HELP_MENU = {
            "Permet de chiffrer votre mot de passe grâce à un algorithme tel que Rot(X), Vigenère, Polybe, Enigma et RC4 \n Cela vous permettra de le déchiffrer par la suite.",
            "Permet de déchiffrer un mot de passe qui a été chiffré par un des algorithmes: Rot(x), Vigenère, Polybe, Enigma et RC4)",
            "Hasher un mot de passe permet de le transformer afin que l'original ne puisse être obtenu, grâce aux algorithmes supportés: MD5 et SHA-256.",
            "Permet de générer un nombre pseudo-aléatoire 4"
    };
    public static final String EXIT = "Sortie du programme.";
    public static final String ENCRYPT_MENU_CHOSEN = "Vous avez choisie le menu de chiffrage";
    public static final String DECRYPT_MENU_CHOSEN = "Vous avez choisie le menu de déchiffrage";
    public static final String HASH_MENU_CHOSEN = "Vous avez choisie le menu de hachage";
    public static final String RANDOM_MENU_CHOSEN = "Vous avez choisie le générateur de nombre";
    public static final String REDO_OR_MENU = "Souhaitez vous recommencer ou retourner au menu ?";
    public static final String VIEW_FILE_MENU_CHOSEN = "Vous avez choisie le menu de visualisation de fichier";
    public static final String HELP_MENU_CHOSEN = "Vous avez choisie le menu d'aide";
    public static final String[] REDO_MENU_CHOICES = { "Recommencer", "Retourner au menu" };
    public static final String[] GO_TO_MENU = { "Retourner au menu ?" };

    public static final String APP_NAME_ASCII =
    " ██████╗██████╗ ██╗   ██╗██████╗ ████████╗ ██████╗     ███████╗ █████╗ ███████╗███████╗\n" +
    "██╔════╝██╔══██╗╚██╗ ██╔╝██╔══██╗╚══██╔══╝██╔═══██╗    ██╔════╝██╔══██╗██╔════╝██╔════╝\n" +
    "██║     ██████╔╝ ╚████╔╝ ██████╔╝   ██║   ██║   ██║    ███████╗███████║█████╗  █████╗  \n" +
    "██║     ██╔══██╗  ╚██╔╝  ██╔═══╝    ██║   ██║   ██║    ╚════██║██╔══██║██╔══╝  ██╔══╝  \n" +
    "╚██████╗██║  ██║   ██║   ██║        ██║   ╚██████╔╝    ███████║██║  ██║██║     ███████╗\n" +
    " ╚═════╝╚═╝  ╚═╝   ╚═╝   ╚═╝        ╚═╝    ╚═════╝     ╚══════╝╚═╝  ╚═╝╚═╝     ╚══════╝";

    // -- CLI -- //
    public static final String ASK_SELECT_OPTION = "Veuillez selectionner une option en entrant sa touche associé.";
    public static final String RETURN_OPTION = "Retour";
    public static final String ASK_VALID_INTEGER = "Veuillez saisir un entier valide.";
    public static final String ASK_VALID_RANGE_0 = "Veuillez saisir un entier compris entre 0 et ";
    public static final String ASK_VALID_RANGE_1 = "Veuillez saisir un entier compris entre 1 et ";

    public static final String ENCRYPTION_ALGORITHM_ALLOWED_CHARACTERS = "L'algorithme que vous avez choisie n'accepte pas les espaces, les chiffres et les caractères spéciaux.";
    public static final String INPUT_WILL_BE_LOWERCASE = "Les majuscules seronts traités comme des minuscules.";
    public static final String INPUT_PASSWORD = "Saisir le mot de passe à chiffrer";
    public static final String INPUT_PASSWORD_TO_DECRYPT = "Saisir le mot de passe à déchiffrer";
    public static final String PASSWORD_ONLY_LETTERS = "Veuillez saisir un mot de passe qui contient uniquement des lettres";
    public static final String DEFINE_POLYBE_SQUARE = "Définir un autre carré de polybe";
    public static final String INPUT_ROT = "Saisir un décalage";
    public static final String INPUT_KEY = "Saisir une clé";
    public static final String POLYBE_SQUARE_SELECTION = "Quelle carré de polybe souhaitez vous utiliser ?";
    public static final String POLYBE_INLINE_SQUARE = "Saisir un carré de polybe sur une ligne";
    public static final String POLYBE_SQUARE_DISPLAY = "Voici votre carré";
    public static final String[] XY_AXIS = {"Abscisse", "Ordonné"};
    public static final String AXIS_READ = "Lire par ordoonée ou abscisse ?";
    public static final String EMPTY_KEY = "La clé ne peut être vide !";
    public static final String ENCRYPTED_PASSWORD_RESULT = "Mot de passe chiffré: ";
    public static final String PASSWORD_STORED_IN = "Le mot de passe chiffré a été stocké dans le fichier ";
    public static final String USER_PROMPT_DECRYPT_METHOD = "Veuillez choisir la méthode de déchiffrage :";
    public static final String DECRYPTED_PASSWORD_RESULT = "Mot de passe déchiffré: ";
    public static final String FILE_DISPLAY = "Voici le contenu du fichier ";
    public static final String GENERATED_NUMBER = "Le nombre a été généré avec succès: ";
    public static final String COMPUTED_HASH = "Hash calculé: ";
}
