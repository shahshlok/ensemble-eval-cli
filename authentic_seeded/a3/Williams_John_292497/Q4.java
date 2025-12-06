import java.util.Scanner;

public class Q4TriangleArea {
    public static void main(String[] args) {
        Scanner inputScanner = new Scanner(System.in);

        System.out.println("Enter three points for a triangle.");

        System.out.print("(x1, y1):");
        double x1 = inputScanner.nextDouble();
        double y1 = inputScanner.nextDouble();

        System.out.print("(x2, y2):");
        double x2 = inputScanner.nextDouble();
        double y2 = inputScanner.nextDouble();

        System.out.print("(x3, y3):");
        double x3 = inputScanner.nextDouble();
        double y3 = inputScanner.nextDouble();

        double side1DifferenceX = x2 - x1;
        double side1DifferenceY = y2 - y1;
        double side1SquareX = side1DifferenceX * side1DifferenceX;
        double side1SquareY = side1DifferenceY * side1DifferenceY;
        double side1SquareSum = side1SquareX + side1SquareY;
        double side1Length = Math.sqrt(side1SquareSum);

        double side2DifferenceX = x3 - x2;
        double side2DifferenceY = y3 - y2;
        double side2SquareX = side2DifferenceX * side2DifferenceX;
        double side2SquareY = side2DifferenceY * side2DifferenceY;
        double side2SquareSum = side2SquareX + side2SquareY;
        double side2Length = Math.sqrt(side2SquareSum);

        double side3DifferenceX = x1 - x3;
        double side3DifferenceY = y1 - y3;
        double side3SquareX = side3DifferenceX * side3DifferenceX;
        double side3SquareY = side3DifferenceY * side3DifferenceY;
        double side3SquareSum = side3SquareX + side3SquareY;
        double side3Length = Math.sqrt(side3SquareSum);

        double sumOfSides = side1Length + side2Length + side3Length;
        double semiPerimeter = sumOfSides / 2.0;

        double sMinusSide1 = semiPerimeter - side1Length;
        double sMinusSide2 = semiPerimeter - side2Length;
        double sMinusSide3 = semiPerimeter - side3Length;

        double productForArea = semiPerimeter * sMinusSide1 * sMinusSide2 * sMinusSide3;
        double triangleArea = Math.sqrt(productForArea);

        System.out.println("The area of the triangle is " + triangleArea);

        inputScanner.close();
    }
}
