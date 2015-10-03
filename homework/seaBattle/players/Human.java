package Lesson4_27_09_2015.homework.seaBattle.players;

import Lesson4_27_09_2015.homework.seaBattle.Cell;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Pattern;

public class Human extends Player {

    private static final Pattern X_COORDINATE_PATTERN = Pattern.compile("[A-Ja-j]");
    private static final Pattern Y_COORDINATE_PATTERN = Pattern.compile("[1-9,10]");

    private enum xCoord {
        A, B, C, D, E, F, G, H, I, J;
    }

    @Override
    public Cell requestTarget() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String userInput;
        int x, y;
        System.out.print("Enter x coordinate (a-j/A-J): ");
        userInput = reader.readLine();
        while (!X_COORDINATE_PATTERN.matcher(userInput).matches()) {
            System.out.print("Incorrect input. Enter x coordinate (a-j/A-J): ");
            userInput = reader.readLine();
        }
        x = xCoord.valueOf(userInput.toUpperCase()).ordinal();
        System.out.print("Enter y coordinate (1-10): ");
        userInput = reader.readLine();
        while (!Y_COORDINATE_PATTERN.matcher(userInput).matches()) {
            System.out.print("Incorrect input. Enter y coordinate (1-10): ");
            userInput = reader.readLine();
        }
        y = Integer.parseInt(userInput);
        reader.close();
        return new Cell(x, y);
    }

    public static void main(String[] args) throws IOException {
        System.out.println(new Human().requestTarget());
    }
}
