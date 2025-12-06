import java.util.Scanner;

public class Q3 {
    public static void main(String[] args) {
        // Create a Scanner to read user input from the console
        Scanner userInputScanner = new Scanner(System.in);

        // Prompt the user for the first point (x1 and y1)
        System.out.print("Enter x1 and y1: ");

        // Read x1 and y1 as doubles
        double x1InputValue = userInputScanner.nextDouble();
        double y1InputValue = userInputScanner.nextDouble();

        // Prompt the user for the second point (x2 and y2)
        System.out.print("Enter x2 and y2: ");

        // Read x2 and y2 as doubles
        double x2InputValue = userInputScanner.nextDouble();
        double y2InputValue = userInputScanner.nextDouble();

        // Compute the difference between x2 and x1
        double xDifferenceValue = x2InputValue - x1InputValue;

        // Compute the difference between y2 and y1
        double yDifferenceValue = y2InputValue - y1InputValue;

        // Square the x difference
        double xDifferenceSquaredValue = xDifferenceValue * xDifferenceValue;

        // Square the y difference
        double yDifferenceSquaredValue = yDifferenceValue * yDifferenceValue;

        // Add the squared differences together
        double sumOfSquaresValue = xDifferenceSquaredValue + yDifferenceSquaredValue;

        // Just to be extra safe, check that the sum is not negative before taking square root
        // (Mathematically it should not be negative, but we are being careful about edge cases)
        if (sumOfSquaresValue < 0) {
            // If something strange happened and it is negative, set it to 0
            sumOfSquaresValue = 0;
        }

        // Take the square root to get the distance between the two points
        double distanceBetweenPointsValue = Math.sqrt(sumOfSquaresValue);

        // Use a temporary holder variable to store the final distance before printing
        double finalDistanceToPrintValue = distanceBetweenPointsValue;

        // Print the result in the required format
        System.out.println("The distance of the two points is " + finalDistanceToPrintValue);

        // Close the scanner to avoid resource leaks
        if (userInputScanner != null) {
            userInputScanner.close();
        }
    }
}