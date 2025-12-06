import java.util.Scanner;

public class Q3 {
    public static void main(String[] args) {
        // Create a Scanner object to read user input from the keyboard
        Scanner userInputScanner = new Scanner(System.in);

        // Prompt the user to enter the first point (x1, y1)
        System.out.print("Enter x1 and y1: ");
        double firstPointXCoordinate = userInputScanner.nextDouble();
        double firstPointYCoordinate = userInputScanner.nextDouble();

        // Prompt the user to enter the second point (x2, y2)
        System.out.print("Enter x2 and y2: ");
        double secondPointXCoordinate = userInputScanner.nextDouble();
        double secondPointYCoordinate = userInputScanner.nextDouble();

        // Compute the horizontal difference: (x2 - x1)
        double horizontalDifference = secondPointXCoordinate - firstPointXCoordinate;

        // Compute the vertical difference: (y2 - y1)
        double verticalDifference = secondPointYCoordinate - firstPointYCoordinate;

        // Declare intermediate math variables a, b, c to structure our formula
        // a will store (x2 - x1)^2
        double a = horizontalDifference * horizontalDifference;

        // b will store (y2 - y1)^2
        double b = verticalDifference * verticalDifference;

        // c will store a + b which is the value inside the square root
        double c = a + b;

        // Finally compute the distance using the square root of c
        double distanceBetweenTwoPoints = Math.sqrt(c);

        // Display the result to the user
        System.out.println("The distance of the two points is " + distanceBetweenTwoPoints);

        // Close the scanner
        userInputScanner.close();
    }
}