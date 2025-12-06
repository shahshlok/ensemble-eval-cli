import java.util.Scanner;

public class Q3 {
    public static void main(String[] args) {
        // Step 1: Create a Scanner to read user input from the keyboard
        Scanner keyboardInputScanner = new Scanner(System.in);

        // Step 2: Ask the user to enter x1 and y1
        System.out.print("Enter x1 and y1: ");
        double firstPointXValue = keyboardInputScanner.nextDouble();
        double firstPointYValue = keyboardInputScanner.nextDouble();

        // Step 3: Ask the user to enter x2 and y2
        System.out.print("Enter x2 and y2: ");
        double secondPointXValue = keyboardInputScanner.nextDouble();
        double secondPointYValue = keyboardInputScanner.nextDouble();

        // Step 4: Compute the difference in x values (x2 - x1)
        double differenceInXValues = secondPointXValue - firstPointXValue;

        // Step 5: Compute the difference in y values (y2 - y1)
        double differenceInYValues = secondPointYValue - firstPointYValue;

        // Step 6: Square the differences ( (x2 - x1)^2 and (y2 - y1)^2 )
        double squaredDifferenceInXValues = differenceInXValues * differenceInXValues;
        double squaredDifferenceInYValues = differenceInYValues * differenceInYValues;

        // Step 7: Add the squared differences
        double sumOfSquaredDifferences = squaredDifferenceInXValues + squaredDifferenceInYValues;

        // Step 8: Take the square root of the sum to get the distance
        double distanceBetweenTwoPoints = Math.sqrt(sumOfSquaredDifferences);

        // Step 9: Print the result for the user
        System.out.println("The distance of the two points is " + distanceBetweenTwoPoints);

        // Step 10: Close the scanner
        keyboardInputScanner.close();
    }
}