import java.util.Scanner;

public class TriangleAreaQ4 {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.println("Enter three points for a triangle.");

        // TODO: maybe make a Point class later?
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

        // TODO: maybe check if triangle is valid (degenerate?) later
        double s = (side1 + side2 + side3) / 2.0;

        double area = Math.sqrt(s * (s - side1) * (s - side2) * (s - side3));

        // TODO: formatting could be nicer
        System.out.println("The area of the triangle is " + area);

        // just some extra loop thing I was playing with to test stuff
        int height = 5;
        for (int row = 1; row < height; row++) {
            // not really needed for the assignment, just wanted to see loops
            System.out.print("");
        }
    }

    public static double distance(double x1, double y1, double x2, double y2) {
        double dx = x2 - x1;
        double dy = y2 - y1;
        double sumSquares = dx * dx + dy * dy;
        return Math.sqrt(sumSquares);
    }
}
