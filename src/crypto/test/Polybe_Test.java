package crypto.test;

import cli.CommandLineInterface;
import crypto.Polybe;
import utils.Verification;

import java.util.List;

public class Polybe_Test {
    public static void main(String[] args) {
        String output;
        String input;
        String selectedPolybeSquare;

        input = "crocodile";

        String[] xy = {"Abscisse", "Ordonné"};

        String[] polybeSquares = {"abcdefghijklmnopqrstuv/wxyz", "abcdefghi/jklmnopqrstuvwxyz"};

        selectedPolybeSquare = "abcdefghijklmnopqrstuv/wxyz";

        CommandLineInterface.DisplayText("Square :");
        Polybe.PrintSquare(selectedPolybeSquare);

        boolean rowFirst = false;

        output = Polybe.encrypt(input, selectedPolybeSquare, rowFirst);

        System.out.println("Encrypt Password : "+output);
        System.out.println(Polybe.decrypt(output, selectedPolybeSquare, rowFirst));
    }
}
