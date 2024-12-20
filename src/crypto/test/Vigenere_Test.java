package crypto.test;

import crypto.Vigenere;

public class Vigenere_Test {
    public static void main(String[] args) {
        Vigenere vigenere = new Vigenere();
        String password;
        String key;
        String encryptPassword;
        String decryptPassword;

        password = "crocidile";
        key = "key";
        encryptPassword = vigenere.encrypt(password,key);

        System.out.println("Encrypt Password : "+ encryptPassword);

        System.out.println("Decrypt Password : "+ vigenere.decrypt(encryptPassword,key));
    }
}
