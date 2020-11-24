package src.boundary;

import java.util.Scanner;

public class InputScanner {

    Scanner scan;

    public InputScanner() {
        scan = new Scanner(System.in);
    }

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

    public int nextInt(int lo) {
        return nextInt(lo, Integer.MAX_VALUE);
    }

}
