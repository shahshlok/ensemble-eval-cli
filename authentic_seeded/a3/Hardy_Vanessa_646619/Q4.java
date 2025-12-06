import java.util.Scanner;

public class Q4TriangleArea {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        double x1, y1, x2, y2, x3, y3;
        double side1, side2, side3, s, area;
        double dx, dy;

        System.out.println("Enter three points for a triangle.");
        System.out.print("(x1, y1):");
        x1 = in.nextDouble();
        y1 = in.nextDouble();

        System.out.print("(x2, y2):");
        x2 = in.nextDouble();
        y2 = in.nextDouble();

        System.out.print("(x3, y3):");
        x3 = in.nextDouble();
        y3 = in.nextDouble();

        dx = x2 - x1;
        dy = y2 - y1;
        side1 = Math.sqrt(dx * dx + dy * dy);

        dx = x3 - x2;
        dy = y3 - y2;
        side2 = Math.sqrt(dx * dx + dy * dy);

        dx = x1 - x3;
        dy = y1 - y3;
        side3 = Math.sqrt(dx * dx + dy * dy);

        s = (side1 + side2 + side3) / 2.0;
        area = Math.sqrt(s * (s - side1) * (s - side2) * (s - side3));

        System.out.println("The area of the triangle is " + area);
    }
}
