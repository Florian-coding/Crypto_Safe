package crypto.test;

import crypto.AES;
import crypto.RC4;

public class RC4_Test {
    public static void main(String[] args) {
        RC4 rc4 = new RC4();
        String password;
        String key;
        String encryptPassword;

        password = "crocodile22!";
        key = "verite";
        encryptPassword = rc4.encrypt(password, key);

        System.out.println("Encrypt Password : " +encryptPassword);

        System.out.println("Decrypt Password : "+rc4.decrypt(encryptPassword, key));
    }
}
