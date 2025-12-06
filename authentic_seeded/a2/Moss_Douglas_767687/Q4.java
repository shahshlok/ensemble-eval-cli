import java.util.Scanner;

public class Q4TriangleArea {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.println("Enter three points for a triangle.");
        System.out.print("(x1, y1):");
        String first = input.nextLine();
        Scanner firstScanner = new Scanner(first);
        double x1 = firstScanner.nextDouble();
        double y1 = firstScanner.nextDouble();
        firstScanner.close();

        System.out.print("(x2, y2):");
        String second = input.nextLine();
        Scanner secondScanner = new Scanner(second);
        double x2 = secondScanner.nextDouble();
        double y2 = secondScanner.nextDouble();
        secondScanner.close();

        System.out.print("(x3, y3):");
        String third = input.nextLine();
        Scanner thirdScanner = new Scanner(third);
        double x3 = thirdScanner.nextDouble();
        double y3 = thirdScanner.nextDouble();
        thirdScanner.close();

        input.close();

        double side1 = distance(x1, y1, x2, y2);
        double side2 = distance(x2, y2, x3, y3);
        double side3 = distance(x3, y3, x1, y1);

        double s = (side1 + side2 + side3) / 2.0;

        double area = Math.sqrt(s * (s - side1) * (s - side2) * (s - side3));

        System.out.println("The area of the triangle is " + area);
    }

    public static double distance(double x1, double y1, double x2, double y2) {
        double dx = x2 - x1;
        double dy = y2 - y1;
        return Math.sqrt(dx * dx + dy * dy);
    }
}
