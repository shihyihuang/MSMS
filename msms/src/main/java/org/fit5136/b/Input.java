package org.fit5136.b;

import java.util.Scanner;

/**
 * Class which is for asking user to enter data using keyboard
 *
 * @author Ng Ka Ho Thomas
 * @version ver1.0.0
 */

public class Input {

    /**
     * A method to return a intput from user input and convert to int
     *
     * @return The input String as int
     */
    public static int acceptNumberInput() {
        try {
            String input = Input.acceptStringInput();
            input = input.trim();
            int number = Integer.parseInt(input);
            return number;
        } catch (NumberFormatException e) {
            // not vaild, ask user to input again lol
            System.out.println("Please input a valid number!!!");
            return acceptNumberInput();
        }
    }

    /**
     * A method to return a intput from user input
     *
     * @return The input String as String
     */
    public static String acceptStringInput() {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        return input;
    }

    /**
     * A method to return a character input from user input
     *
     * @return The input character
     */
    public static char acceptCharInput() {
        try {
            String input = acceptStringInput();
            if (input.length() == 1) {
                char character = input.charAt(0);
                return character;
            } else {
                System.out.println("Please input a single character!!!");
                return acceptCharInput();
            }
        } catch (NumberFormatException e) {
            // not valid, ask user to input again
            System.out.println("Please input a valid character!!!");
            return acceptCharInput();
        }
    }

}