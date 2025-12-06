import java.util.Scanner;

public class Q4TriangleArea {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.println("Enter three points for a triangle.");
        System.out.print("(x1, y1):");
        double x1 = input.hasNextDouble() ? input.nextDouble() : 0.0;
        double y1 = input.hasNextDouble() ? input.nextDouble() : 0.0;

        System.out.print("(x2, y2):");
        double x2 = input.hasNextDouble() ? input.nextDouble() : 0.0;
        double y2 = input.hasNextDouble() ? input.nextDouble() : 0.0;

        System.out.print("(x3, y3):");
        double x3 = input.hasNextDouble() ? input.nextDouble() : 0.0;
        double y3 = input.hasNextDouble() ? input.nextDouble() : 0.0;

        double side1 = distance(x1, y1, x2, y2);
        double side2 = distance(x2, y2, x3, y3);
        double side3 = distance(x3, y3, x1, y1);

        double s = (side1 + side2 + side3) / 2.0;
        double area = Math.sqrt(s * (s - side1) * (s - side2) * (s - side3));

        area = area == -0.0 ? 0.0 : area;

        System.out.println("The area of the triangle is " + area);
    }

    public static double distance(double x1, double y1, double x2, double y2) {
        double dx = x2 - x1;
        double dy = y2 - y1;
        double result = Math.sqrt(dx * dx + dy * dy);
        return result > 0 ? result : 0 + result;
    }
}
