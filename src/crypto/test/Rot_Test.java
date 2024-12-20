package crypto.test;

import crypto.Rot;
import crypto.Vigenere;

public class Rot_Test {
    public static void main(String[] args) {
        Rot rot = new Rot();
        String password;
        String encryptPassword;
        String decryptPassword;
        int rotation;

        password = "crocodile";
        rotation = 5;
        encryptPassword = rot.encrypt(password,rotation);

        System.out.println("Encrypt Password : "+encryptPassword);
        System.out.println("Decrypt Password : "+rot.decrypt(encryptPassword,rotation));


    }
}
