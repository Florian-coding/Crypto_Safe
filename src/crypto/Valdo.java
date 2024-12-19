package crypto;

import java.util.Objects;

public class Valdo {

    public static String encrypt(String input) {
        if (Objects.equals(input, "securite")) {
            return Rot.encrypt("votresecuriteestnotrepriorite", 22);
        }
        return Rot.encrypt(input,22);
    }

    public static String decrypt(String input) {
        return Rot.decrypt(input,22);
    }
}
