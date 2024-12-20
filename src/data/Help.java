package data;

public class Help {
    public static final String HELP_ENCRYPT =
    "Le chiffrement consiste à transformer un texte en un autre texte, de manière à ce que le texte original ne soit pas lisible.";

    public static final String HELP_DECRYPT =
    "Le déchiffrement consiste à retrouver le texte original à partir d'un texte chiffré.";

    public static final String HELP_ENCRYPT_ROT =
    "Le chiffrement par rotation X consiste à décaler chaque lettre d'un texte d'un certain nombre de positions dans l'alphabet.\n"+
    "Par exemple, avec une rotation de 3, \"SECURITE\" devient \"VHFXULWH\", et pour déchiffrer, on fait l'inverse en reculant de 3 positions.\n";

    public static final String HELP_ENCRYPT_ENIGMA =
    "Le chiffrement Enigma repose sur une machine électromécanique qui transforme chaque lettre en une autre selon un système complexe de rotors, reflétant un décalage qui change à chaque lettre tapée.\n"+
    "Par exemple, \"SECURITE\" pourrait devenir \"XJPNWUVG\", et le message ne peut être déchiffré qu'en connaissant la configuration exacte des rotors utilisée au départ.";
    
    public static final String HELP_ENCRYPT_VIGENERE = "Le chiffrement de Vigenère utilise un mot-clé pour chiffrer un message en décalant chaque lettre selon la position correspondante dans le mot-clé.\n"+
    "Par exemple, si le message est \"SECURITE\" et le mot-clé est \"CLE\", chaque lettre du message est décalée en fonction de la lettre correspondante dans le mot-clé, \n" +
    "ce qui donne un texte comme \"UIGEVMXG\". Pour déchiffrer, il suffit d'appliquer le mot-clé en sens inverse.";

    public static final String HELP_ENCRYPT_POLYBE =
    "Le chiffrement de Polybe transforme les lettres d’un message en paires de chiffres en utilisant une grille.\n"+
    "Par exemple, dans une grille où les lettres sont disposées dans un tableau de 5 colonnes et 5 lignes, la lettre \"S\" peut être codée par \"43\" si elle est dans la 4ᵉ ligne et la 3ᵉ colonne.\n"+
    "Ainsi, \"SECURITE\" devient une suite de chiffres comme \"43 15 31 45 24 15 44 15\", et pour déchiffrer, il suffit de retrouver les lettres correspondantes dans la grille.";
    
    public static final String HELP_ENCRYPT_VALDO =
    "L'algorithme Valdo est un système de chiffrement qui fonctionne comme une rotation X, mais avec une rotation fixe de 22. Il transforme chaque lettre en la décalant de 22 positions dans l'alphabet,\n" +
    "créant un texte qui semble incompréhensible. Par exemple, avec Valdo, le mot \"valdo\" devient \"rwhzk\".\n" +
    "Ce type de chiffrement est idéal pour cacher des messages... et qui sait, peut-être qu'en cherchant bien, un mot comme \"securite\" pourrait lui-même révéler un petit secret.";
    
    public static final String HELP_ENCRYPT_RC4 =
    "Le chiffrement RC4 utilise une clé secrète pour mélanger l'ordre des données du message, rendant son contenu illisible sans cette clé.\n" +
    "Par exemple, si on chiffre \"SECURITE\" avec une clé, le message devient une série de caractères comme \"Gx1@z%8Q\".\n" +
    "Pour le déchiffrer, il faut utiliser la même clé, qui permet de retrouver le message original.";
    
    public static final String HELP_ENCRYPT_AES =
    "Le chiffrement AES (Advanced Encryption Standard) est une méthode moderne et très sécurisée utilisée pour protéger les données.\n"+
    "Il fonctionne en prenant un texte et une clé secrète pour mélanger les données du message de manière complexe, grâce à plusieurs étapes de substitution,\n" +
    "de permutation et de transformation mathématique. Le résultat est un texte chiffré qui semble totalement aléatoire, par exemple \"7dF5gH3K\".";

    public static final String HELP_HASH =
    "Le hash est une méthode qui transforme un message en une suite unique de caractères, appelée empreinte, comme une sorte d'empreinte digitale. \n"+
    "Par exemple, \"SECURITE\" peut devenir \"5e88489...\". Contrairement au chiffrement, le hash n'est pas réversible: on ne peut pas retrouver le message original à partir de l'empreinte. \n"+
    "Le chiffrement, lui, permet de cacher un message de façon réversible en utilisant une clé secrète pour le chiffrer et le déchiffrer.";

    public static final String HELP_MD5 =
    "MD5 est une méthode de hashage qui transforme un message en une empreinte unique et fixe, composée de 32 caractères hexadécimaux. \n"+
    "Par exemple, \"SECURITE\" devient \"96ec1728b7b88ca8a3f1f233d3db1e0b\". Contrairement au chiffrement, MD5 est irréversible: on ne peut pas retrouver le message d'origine à partir de l'empreinte.\n"+
    "Cependant, comme MD5 n'est plus considéré comme totalement sûr, il n'est pas recommandé pour des applications nécessitant une sécurité élevée.";

    public static final String HELP_SHA256 =
    "SHA-256 est une méthode de hashage qui transforme un message en une empreinte unique composée de 64 caractères hexadécimaux. Par exemple, \"SECURITE\" devient:\n"+
    "b5a47f3c86592f2d6936c474e3a7cf06b1b211e8eecbe356b6cba3cf673773a7.";

    public static final String HELP_HMAC =
    "HMAC (Hash-Based Message Authentication Code) combine un hash comme SHA-256 avec une clé secrète pour créer une empreinte unique et sécurisée d'un message.\n" +
    "Par exemple, en utilisant \"SECURITE\" et une clé secrète, on obtient une empreinte comme: 3c5e9fa6d62f91f3c24aee28c3c7f5c20e5c2431df8b6f11f5ed7d8a.";

    public static final String[] HELP_SELECTION = {HELP_ENCRYPT_ROT, HELP_ENCRYPT_VIGENERE, HELP_ENCRYPT_POLYBE, HELP_ENCRYPT_VALDO, HELP_ENCRYPT_ENIGMA, HELP_ENCRYPT_RC4, HELP_ENCRYPT_AES, HELP_MD5, HELP_SHA256, HELP_HMAC};
}
