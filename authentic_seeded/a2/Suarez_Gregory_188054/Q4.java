import java.util.Scanner;

public class Q4TriangleArea {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.println("Enter three points for a triangle.");

        System.out.print("(x1, y1):");
        double y1 = input.nextDouble();
        double x1 = input.nextDouble();

        System.out.print("(x2, y2):");
        double x2 = input.nextDouble();
        double y2 = input.nextDouble();

        System.out.print("(x3, y3):");
        double x3 = input.nextDouble();
        double y3 = input.nextDouble();

        double side1DeltaX = x2 - x1;
        double side1DeltaY = y2 - y1;
        double side1DeltaXSquared = side1DeltaX * side1DeltaX;
        double side1DeltaYSquared = side1DeltaY * side1DeltaY;
        double side1SumOfSquares = side1DeltaXSquared + side1DeltaYSquared;
        double side1Length = Math.sqrt(side1SumOfSquares);

        double side2DeltaX = x3 - x2;
        double side2DeltaY = y3 - y2;
        double side2DeltaXSquared = side2DeltaX * side2DeltaX;
        double side2DeltaYSquared = side2DeltaY * side2DeltaY;
        double side2SumOfSquares = side2DeltaXSquared + side2DeltaYSquared;
        double side2Length = Math.sqrt(side2SumOfSquares);

        double side3DeltaX = x1 - x3;
        double side3DeltaY = y1 - y3;
        double side3DeltaXSquared = side3DeltaX * side3DeltaX;
        double side3DeltaYSquared = side3DeltaY * side3DeltaY;
        double side3SumOfSquares = side3DeltaXSquared + side3DeltaYSquared;
        double side3Length = Math.sqrt(side3SumOfSquares);

        double sumOfSides = side1Length + side2Length + side3Length;
        double s = sumOfSides / 2.0;

        double sMinusSide1 = s - side1Length;
        double sMinusSide2 = s - side2Length;
        double sMinusSide3 = s - side3Length;

        double productUnderRoot = s * sMinusSide1 * sMinusSide2 * sMinusSide3;
        double area = Math.sqrt(productUnderRoot);

        System.out.println("The area of the triangle is " + area);
    }
}
