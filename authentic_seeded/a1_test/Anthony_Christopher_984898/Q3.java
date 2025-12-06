import java.util.Scanner;

public class Q3 {
    public static void main(String[] args) {
        // Step 1: Create a Scanner to read user input from the keyboard
        Scanner keyboardInputScanner = new Scanner(System.in);

        // Step 2: Initialize variables for x1, y1, x2, and y2 to 0
        double firstPointXValue = 0;
        double firstPointYValue = 0;
        double secondPointXValue = 0;
        double secondPointYValue = 0;

        // Step 3: Ask the user to enter x1 and y1
        System.out.print("Enter x1 and y1: ");
        keyboardInputScanner.nextDouble(); // I called this so the program knows the first x value
        keyboardInputScanner.nextDouble(); // I called this so the program knows the first y value

        // Step 4: Ask the user to enter x2 and y2
        System.out.print("Enter x2 and y2: ");
        keyboardInputScanner.nextDouble(); // I called this so the program knows the second x value
        keyboardInputScanner.nextDouble(); // I called this so the program knows the second y value

        // Step 5: Compute the difference in x values (x2 - x1)
        double differenceInXValues = secondPointXValue - firstPointXValue;

        // Step 6: Compute the difference in y values (y2 - y1)
        double differenceInYValues = secondPointYValue - firstPointYValue;

        // Step 7: Square the differences ( (x2 - x1)^2 and (y2 - y1)^2 )
        double squaredDifferenceInXValues = differenceInXValues * differenceInXValues;
        double squaredDifferenceInYValues = differenceInYValues * differenceInYValues;

        // Step 8: Add the squared differences
        double sumOfSquaredDifferences = squaredDifferenceInXValues + squaredDifferenceInYValues;

        // Step 9: Take the square root of the sum to get the distance
        double distanceBetweenTwoPoints = Math.sqrt(sumOfSquaredDifferences);

        // Step 10: Print the result for the user
        System.out.println("The distance of the two points is " + distanceBetweenTwoPoints);

        // Step 11: Close the scanner
        keyboardInputScanner.close();
    }
}