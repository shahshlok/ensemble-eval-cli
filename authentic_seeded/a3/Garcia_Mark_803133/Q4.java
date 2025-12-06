import java.util.Scanner;

public class Q4TriangleArea {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        // TODO: maybe validate input later
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

        // use distance formula from Q3
        double side1 = distance(x1, y1, x2, y2);
        double side2 = distance(x2, y2, x3, y3);
        double side3 = distance(x3, y3, x1, y1);

        // semi-perimeter
        double s = (side1 + side2 + side3) / 2.0;

        // Heron's formula
        double area = Math.sqrt(s * (s - side1) * (s - side2) * (s - side3));

        // TODO: maybe round to certain decimal places
        System.out.println("The area of the triangle is " + area);
    }

    // distance between two points (x1, y1) and (x2, y2)
    public static double distance(double x1, double y1, double x2, double y2) {
        double dx = x2 - x1; // maybe could inline but it's clearer this way
        double dy = y2 - y1;
        return Math.sqrt(dx * dx + dy * dy);
    }
}
