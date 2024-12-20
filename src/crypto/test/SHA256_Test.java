package crypto.test;

import crypto.MD5;
import crypto.SHA256;

public class SHA256_Test {
    public static void main(String[] args) {
        SHA256 sha256 = new SHA256();
        String password1;
        String password2;
        String hashPassword1;
        String hashPassword2;

        password1 = "crocidile22!";
        hashPassword1 = sha256.hash(password1);

        password2 = "crocidile22!";
        hashPassword2 = sha256.hash(password2);

        System.out.println("Hash Password : "+ hashPassword1);
        System.out.println("Hash Password : "+ hashPassword2);

        System.out.println(sha256.CheckSum(password1, password2) ? "CheckSum ok" : "CheckSum not ok");
    }
}
