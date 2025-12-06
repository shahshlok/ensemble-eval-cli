import java.util.Scanner;

public class Q4TriangleArea {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.println("Enter three points for a triangle.");
        System.out.print("(x1, y1):");
        double x1 = input.nextDouble();
        double y1 = input.nextDouble();

        System.out.print("(x2, y2):");
        double x2 = input.nextDouble();
        double y2 = input.nextDouble();

        System.out.print("(x3, y3):");
        double x3 = input.nextDouble();
        double y3 = input.nextDouble();

        double side1 = distanceBetweenPoints(x1, y1, x2, y2);
        double side2 = distanceBetweenPoints(x2, y2, x3, y3);
        double side3 = distanceBetweenPoints(x3, y3, x1, y1);

        double area = calculateTriangleArea(side1, side2, side3);

        System.out.println("The area of the triangle is " + area);

        input.close();
    }

    public static double distanceBetweenPoints(double x1, double y1, double x2, double y2) {
        double xDiff = x2 - x1;
        double yDiff = y2 - y1;
        double xDiffSquared = xDiff * xDiff;
        double yDiffSquared = yDiff * yDiff;
        double sum = xDiffSquared + yDiffSquared;
        double distance = Math.sqrt(sum);
        return distance;
    }

    public static double calculateTriangleArea(double side1, double side2, double side3) {
        double s = calculateSemiPerimeter(side1, side2, side3);
        double area = Math.sqrt(s * (s - side1) * (s - side2) * (s - side3));
        return area;
    }

    public static double calculateSemiPerimeter(double side1, double side2, double side3) {
        double s = (side1 + side2 + side3) / 2.0;
        return s;
    }
}
