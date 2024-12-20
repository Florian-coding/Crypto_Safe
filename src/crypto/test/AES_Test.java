package crypto.test;

import crypto.AES;
import crypto.Vigenere;

public class AES_Test {
    public static void main(String[] args) {
        AES aes = new AES();
        String password;
        String key;
        String encryptPassword;

        password = "crocodile22!";
        key = "verite";
        encryptPassword = aes.encrypt(password, key);

        System.out.println("Encrypt Password : " +encryptPassword);

        System.out.println("Decrypt Password : "+aes.decrypt(encryptPassword, key));
    }
}
