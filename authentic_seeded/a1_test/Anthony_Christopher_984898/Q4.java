import java.util.Scanner;

public class Q4 {
    public static void main(String[] args) {

        // Create a Scanner object to read user input from the keyboard
        Scanner userInputScanner = new Scanner(System.in);

        // Tell the user what the program needs
        System.out.println("Enter three points for a triangle.");

        // Ask the user to enter the first point (x1, y1)
        System.out.print("(x1, y1):");
        double x1Value = userInputScanner.nextDouble();
        double y1Value = userInputScanner.nextDouble();

        // Ask the user to enter the second point (x2, y2)
        System.out.print("(x2, y2):");
        double x2Value = userInputScanner.nextDouble();
        double y2Value = userInputScanner.nextDouble();

        // Ask the user to enter the third point (x3, y3)
        System.out.print("(x3, y3):");
        double x3Value = userInputScanner.nextDouble();
        double y3Value = userInputScanner.nextDouble();

        // Calculate the length of side1 using distance formula between point1 and point2
        double side1DifferenceX = x2Value - x1Value;
        double side1DifferenceY = y2Value - y1Value;
        double side1Length = Math.sqrt(side1DifferenceX * side1DifferenceX + side1DifferenceY * side1DifferenceY);

        // Calculate the length of side2 using distance formula between point2 and point3
        double side2DifferenceX = x3Value - x2Value;
        double side2DifferenceY = y3Value - y2Value;
        double side2Length = Math.sqrt(side2DifferenceX * side2DifferenceX + side2DifferenceY * side2DifferenceY);

        // Calculate the length of side3 using distance formula between point3 and point1
        double side3DifferenceX = x1Value - x3Value;
        double side3DifferenceY = y1Value - y3Value;
        double side3Length = Math.sqrt(side3DifferenceX * side3DifferenceX + side3DifferenceY * side3DifferenceY);

        // Calculate the semi-perimeter s using the formula s = (side1 + side2 + side3) / 2
        double semiPerimeterValue = (side1Length + side2Length + side3Length) / 2.0;

        // Calculate the area using Heron's formula
        double triangleAreaValue = Math.sqrt(
                semiPerimeterValue
                        * (semiPerimeterValue - side1Length)
                        * (semiPerimeterValue - side2Length)
                        * (semiPerimeterValue - side3Length)
        );

        // Display the area of the triangle to the user
        System.out.println("The area of the triangle is " + triangleAreaValue);

        // Close the scanner to free resources
        userInputScanner.close();
    }
}