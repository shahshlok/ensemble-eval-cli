import java.util.Scanner;

public class TriangleAreaHeron {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.println("Enter three points for a triangle.");
        double x1 = readCoordinate(input, "(x1, y1):", true);
        double y1 = readCoordinate(input, "", false);

        double x2 = readCoordinate(input, "(x2, y2):", true);
        double y2 = readCoordinate(input, "", false);

        double x3 = readCoordinate(input, "(x3, y3):", true);
        double y3 = readCoordinate(input, "", false);

        double side1 = distanceBetweenPoints(x1, y1, x2, y2);
        double side2 = distanceBetweenPoints(x2, y2, x3, y3);
        double side3 = distanceBetweenPoints(x3, y3, x1, y1);

        double area = calculateTriangleArea(side1, side2, side3);

        System.out.println("The area of the triangle is " + area);
    }

    public static double readCoordinate(Scanner input, String prompt, boolean printPrompt) {
        if (printPrompt) {
            System.out.print(prompt);
        }
        return input.nextDouble();
    }

    public static double distanceBetweenPoints(double x1, double y1, double x2, double y2) {
        double deltaX = x2 - x1;
        double deltaY = y2 - y1;
        return Math.sqrt(deltaX * deltaX + deltaY * deltaY);
    }

    public static double calculateTriangleArea(double side1, double side2, double side3) {
        double s = side1 + side2 + side3 / 2;
        double area = Math.sqrt(s * (s - side1) * (s - side2) * (s - side3));
        return area;
    }
}
