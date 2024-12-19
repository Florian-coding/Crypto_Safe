package data;

public class Texts {
    // --- MENU -- //
    public static final String APP_NAME = "CRYPTO SAFE";
    public static final String WELCOME = "Bienvenue sur notre gestionnaire de mot de passe exceptionnel !";
    public static final String[] MAIN_MENU = { "Chiffrer un mot de passe", "Déchiffrer un mot de passe", "Hacher un mot de passe", "Générer un nombre entier pseudo-aléatoire", "Cacher un message en stéganographie","Voir mes mots de passes","Quitter" };
    public static final String[] ENCRYPTION_METHODS = { "ROT(X)", "Vigenère", "Polybe", "Valdo", "Enigma", "RC4", "AES" };
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
    public static final String[] REDO_MENU_CHOICES = { "Recommencer", "Retourner au menu" };

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
}
