package src.boundary;

import java.util.Scanner;

/**
 * Scanner class that deals with all integer inputs
 */
public class InputScanner {

    Scanner scan;

    /**
     * Constructor of the scanner class which
     * initializes a scanner object
     */
    public InputScanner() {
        scan = new Scanner(System.in);
    }

    /**
     * Reads the next input and returns only when
     * the input is within range lo and hi
     * @param lo the lower bound
     * @param hi the upper bound(exclusive)
     * @return the user input
     */
    public int nextInt(int lo, int hi) {
        boolean success = false;
        int input = 0;
        while (!success) {
            String in = scan.nextLine();
            try {
                input = Integer.parseInt(in);
                if (input >= hi || input < lo) {
                    System.out.println("Input out of bound");
                    System.out.printf("Option: ");
                } else {
                    success = true;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input");
                System.out.printf("Input: ");
            }
        }
        return input;
    }

    /**
     * Reads the next input and returns only when
     * the input is larger than the lo
     * @param lo the lower bound
     * @return the user input
     */
    public int nextInt(int lo) {
        return nextInt(lo, Integer.MAX_VALUE);
    }

}
