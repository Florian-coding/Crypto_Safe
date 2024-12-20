package crypto.test;

import crypto.MD5;

public class MD5_Test {
    public static void main(String[] args) {
        MD5 md5 = new MD5();
        String password;
        String hashPassword1;
        String hashPassword2;

        password = "crocidile22!";
        hashPassword1 = md5.hash(password);
        hashPassword2 = md5.hash(password);

        System.out.println("Hash Password 1 : "+ hashPassword1);
        System.out.println("Hash Password 2 : "+ hashPassword2);

        System.out.println(md5.CheckSum(hashPassword1, hashPassword2) ? "CheckSum ok" : "CheckSum not ok");
    }
}
