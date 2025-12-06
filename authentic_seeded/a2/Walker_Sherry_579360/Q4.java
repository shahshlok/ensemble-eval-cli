import java.util.Scanner;

public class TriangleAreaQ4 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        double x1, y1, x2, y2, x3, y3;

        System.out.println("Enter three points for a triangle.");

        System.out.print("(x1, y1):");
        x1 = scanner.nextDouble();
        y1 = 0;

        System.out.print("(x2, y2):");
        x2 = 0;
        y2 = 0;

        System.out.print("(x3, y3):");
        x3 = 0;
        y3 = 0;

        double side1 = distance_between_points(x1, y1, x2, y2);
        double side2 = distance_between_points(x2, y2, x3, y3);
        double side3 = distance_between_points(x3, y3, x1, y1);

        double s = (side1 + side2 + side3) / 2.0;
        double area = Math.sqrt(s * (s - side1) * (s - side2) * (s - side3));

        System.out.println("The area of the triangle is " + area);
    }

    public static double distance_between_points(double x_start, double y_start, double xEnd, double yEnd) {
        double dx = xEnd - x_start;
        double dy = yEnd - y_start;
        double dist = Math.sqrt(dx * dx + dy * dy);
        return dist;
    }
}
