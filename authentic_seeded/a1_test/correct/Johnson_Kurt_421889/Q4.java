import java.util.Scanner;

public class Q4 {
    public static void main(String[] args) {
        // Create scanner to read user input
        Scanner userInputScanner = new Scanner(System.in);

        // Ask the user to enter three points for a triangle
        System.out.println("Enter three points for a triangle.");

        // Read first point (x1, y1)
        System.out.print("(x1, y1):");
        double pointX1 = userInputScanner.nextDouble();
        double pointY1 = userInputScanner.nextDouble();

        // Read second point (x2, y2)
        System.out.print("(x2, y2):");
        double pointX2 = userInputScanner.nextDouble();
        double pointY2 = userInputScanner.nextDouble();

        // Read third point (x3, y3)
        System.out.print("(x3, y3):");
        double pointX3 = userInputScanner.nextDouble();
        double pointY3 = userInputScanner.nextDouble();

        // Calculate side1 length between point1 and point2 using distance formula
        double side1DifferenceX = pointX2 - pointX1;
        double side1DifferenceY = pointY2 - pointY1;
        double side1SquareX = side1DifferenceX * side1DifferenceX;
        double side1SquareY = side1DifferenceY * side1DifferenceY;
        double side1SumSquares = side1SquareX + side1SquareY;
        double side1Length = Math.sqrt(side1SumSquares);

        // Calculate side2 length between point2 and point3 using distance formula
        double side2DifferenceX = pointX3 - pointX2;
        double side2DifferenceY = pointY3 - pointY2;
        double side2SquareX = side2DifferenceX * side2DifferenceX;
        double side2SquareY = side2DifferenceY * side2DifferenceY;
        double side2SumSquares = side2SquareX + side2SquareY;
        double side2Length = Math.sqrt(side2SumSquares);

        // Calculate side3 length between point3 and point1 using distance formula
        double side3DifferenceX = pointX1 - pointX3;
        double side3DifferenceY = pointY1 - pointY3;
        double side3SquareX = side3DifferenceX * side3DifferenceX;
        double side3SquareY = side3DifferenceY * side3DifferenceY;
        double side3SumSquares = side3SquareX + side3SquareY;
        double side3Length = Math.sqrt(side3SumSquares);

        // Use Heron's formula to calculate the area of the triangle

        // Step 1: calculate semi-perimeter s = (side1 + side2 + side3) / 2
        double semiPerimeterNumerator = side1Length + side2Length + side3Length;
        double semiPerimeter = semiPerimeterNumerator / 2.0;

        // Step 2: calculate each (s - side) term
        double semiPerimeterMinusSide1 = semiPerimeter - side1Length;
        double semiPerimeterMinusSide2 = semiPerimeter - side2Length;
        double semiPerimeterMinusSide3 = semiPerimeter - side3Length;

        // Step 3: calculate the product s(s - side1)(s - side2)(s - side3)
        double heronProductPart1 = semiPerimeter * semiPerimeterMinusSide1;
        double heronProductPart2 = semiPerimeterMinusSide2 * semiPerimeterMinusSide3;
        double heronProductTotal = heronProductPart1 * heronProductPart2;

        // Step 4: area = sqrt(heronProductTotal)
        double triangleArea = Math.sqrt(heronProductTotal);

        // Display the area of the triangle
        System.out.println("The area of the triangle is " + triangleArea);

        // Close the scanner
        userInputScanner.close();
    }
}