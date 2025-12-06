import java.util.Scanner;

public class Q4TriangleArea {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        System.out.println("Enter three points for a triangle.");
        System.out.print("(x1, y1):");
        double x1 = in.nextDouble();
        double y1 = in.nextDouble();

        System.out.print("(x2, y2):");
        double x2 = in.nextDouble();
        double y2 = in.nextDouble();

        System.out.print("(x3, y3):");
        double x3 = in.nextDouble();
        double y3 = in.nextDouble();

        double dx = x2 - x1;
        double dy = y2 - y1;
        double side1 = Math.sqrt(dx * dx + dy * dy);

        dx = x3 - x2;
        dy = y3 - y2;
        double side2 = Math.sqrt(dx * dx + dy * dy);

        dx = x1 - x3;
        dy = y1 - y3;
        double side3 = Math.sqrt(dx * dx + dy * dy);

        double s = (side1 + side2 + side3) / 2.0;
        double area = Math.sqrt(s * (s - side1) * (s - side2) * (s - side3));

        System.out.println("The area of the triangle is " + area);
    }
}
