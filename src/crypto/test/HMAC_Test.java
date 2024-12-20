package crypto.test;

import crypto.HMAC;

public class HMAC_Test {
    public static void main(String[] args) {
        HMAC hmac = new HMAC();
        String password;
        String key;
        String hashPassword1;
        String hashPassword2;

        password = "crocodile22!";
        key = "verite";

        hashPassword1 = hmac.hash(password,key);
        hashPassword2 = hmac.hash(password,key);

        System.out.println("Hash Password 1" +hashPassword1);
        System.out.println("Hash Password 2" +hashPassword2);

        System.out.println(hmac.CheckSum(hashPassword1, hashPassword2) ? "CheckSum ok" : "CheckSum not ok");
    }
}
