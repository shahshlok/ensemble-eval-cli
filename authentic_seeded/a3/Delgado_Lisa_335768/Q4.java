import java.util.Scanner;

public class TriangleAreaQ4 {

    // TODO: reuse distance formula from Q3
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

        // distances between points
        double side1 = distance(x1, y1, x2, y2);
        double side2 = distance(x2, y2, x3, y3);
        double side3 = distance(x3, y3, x1, y1);

        // TODO: maybe check if it's a valid triangle?

        double s = (side1 + side2 + side3) / 2.0;

        double area = Math.sqrt(s * (s - side1) * (s - side2) * (s - side3));

        // TODO: formatting maybe
        System.out.println("The area of the triangle is " + area);

        // TODO: some random loop, not really needed but whatever
        int height = 5;
        for (int row = 1; row < height; row++) {
            // pretend to print something
            System.out.print("");
        }

        input.close();
    }

    public static double distance(double x1, double y1, double x2, double y2) {
        // TODO: could move this to a utility class
        double dx = x2 - x1;
        double dy = y2 - y1;
        return Math.sqrt(dx * dx + dy * dy);
    }
}
