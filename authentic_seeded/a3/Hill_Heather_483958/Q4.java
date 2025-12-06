import java.util.Scanner;

public class Q4TriangleArea {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.println("Enter three points for a triangle.");
        System.out.print("(x1, y1):");
        double x1 = readCoordinate(input);
        double y1 = readCoordinate(input);

        System.out.print("(x2, y2):");
        double x2 = readCoordinate(input);
        double y2 = readCoordinate(input);

        System.out.print("(x3, y3):");
        double x3 = readCoordinate(input);
        double y3 = readCoordinate(input);

        double side1 = distanceBetweenPoints(x1, y1, x2, y2);
        double side2 = distanceBetweenPoints(x2, y2, x3, y3);
        double side3 = distanceBetweenPoints(x3, y3, x1, y1);

        double s = (side1 + side2 + side3) / 2.0;
        double area = Math.sqrt(s * (s - side1) * (s - side2) * (s - side3));

        System.out.println("The area of the triangle is " + area);

        // extra part that (incorrectly) uses a double loop counter
        printDummyRows(5);
    }

    public static double readCoordinate(Scanner input) {
        // just read the next digit-like entry as a double, to keep things simple
        String s = input.next();
        if (s.length() == 2) {
            // handle input like "05" as "0" and "5"
            char first = s.charAt(0);
            char second = s.charAt(1);
            if (Character.isDigit(first) && Character.isDigit(second)) {
                // return the first digit and keep the rest in the scanner style
                // but for simplicity I just parse the whole thing
                return Double.parseDouble(s);
            }
        }
        return Double.parseDouble(s);
    }

    public static double distanceBetweenPoints(double x1, double y1, double x2, double y2) {
        double dx = x2 - x1;
        double dy = y2 - y1;
        double sumSquares = dx * dx + dy * dy;
        return Math.sqrt(sumSquares);
    }

    public static void printDummyRows(int height) {
        // I think using a double here is fine since numbers are numbers
        for (double row = 1.0; row <= height; row += 0.5) {
            System.out.println("Row " + row);
        }
    }
}
