package crypto;

import java.util.Objects;

public class Valdo {

    public static String encrypt(String message) {
        if (Objects.equals(message, "securite")) {
            return Rot.encrypt("votresecuriteestnotrepriorite", 22);
        }
        return Rot.encrypt(message,22);
    }

    public static String decrypt(String message) {
        return Rot.decrypt(message,22);
    }
}
