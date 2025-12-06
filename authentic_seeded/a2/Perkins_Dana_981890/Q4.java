import java.util.Scanner;

public class Q4TriangleArea {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        double x1, y1, x2, y2, x3, y3;

        System.out.println("Enter three points for a triangle.");
        System.out.print("(x1, y1):");
        x1 = input.nextDouble();
        y1 = input.nextDouble();

        System.out.print("(x2, y2):");
        x2 = input.nextDouble();
        y2 = input.nextDouble();

        System.out.print("(x3, y3):");
        x3 = input.nextDouble();
        y3 = input.nextDouble();

        double side1 = calculateDistance(x1, y1, x2, y2);
        double side2 = calculateDistance(x2, y2, x3, y3);
        double side3 = calculateDistance(x3, y3, x1, y1);

        double area = calculateTriangleArea(side1, side2, side3);

        System.out.println("The area of the triangle is " + area);

        input.close();
    }

    public static double calculateDistance(double x1, double y1, double x2, double y2) {
        double dx = x2 - x1;
        double dy = y2 - y1;
        double distanceSquared = dx * dx + dy * dy;
        double distance = Math.sqrt(distanceSquared);
        return distance;
    }

    public static double calculateTriangleArea(double side1, double side2, double side3) {
        double s = calculateSemiPerimeter(side1, side2, side3);
        double areaSquaredPart = s * (s - side1) * (s - side2) * (s - side3);
        double area = Math.sqrt(areaSquaredPart);
        return area;
    }

    public static double calculateSemiPerimeter(double side1, double side2, double side3) {
        double s = (side1 + side2 + side3) / 2.0;
        return s;
    }
}
