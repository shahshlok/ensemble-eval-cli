import java.util.Scanner;

public class Q4TriangleArea {
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

        // debug prints
        System.out.println("Debug: Point1 = (" + x1 + ", " + y1 + ")");
        System.out.println("Debug: Point2 = (" + x2 + ", " + y2 + ")");
        System.out.println("Debug: Point3 = (" + x3 + ", " + y3 + ")");

        // Use Q3-style distance formula for each side
        double temp1 = x2 - x1;
        double temp2 = y2 - y1;
        double side1 = Math.sqrt(temp1 * temp1 + temp2 * temp2);
        System.out.println("Debug: side1 = " + side1);

        temp1 = x3 - x2;
        temp2 = y3 - y2;
        double side2 = Math.sqrt(temp1 * temp1 + temp2 * temp2);
        System.out.println("Debug: side2 = " + side2);

        temp1 = x3 - x1;
        temp2 = y3 - y1;
        double side3 = Math.sqrt(temp1 * temp1 + temp2 * temp2);
        System.out.println("Debug: side3 = " + side3);

        // Heron's formula
        double s = (side1 + side2 + side3) / 2.0;
        System.out.println("Debug: semi-perimeter s = " + s);

        double tempAreaHolder1 = s - side1;
        double tempAreaHolder2 = s - side2;
        double tempAreaHolder3 = s - side3;
        System.out.println("Debug: s-side1 = " + tempAreaHolder1);
        System.out.println("Debug: s-side2 = " + tempAreaHolder2);
        System.out.println("Debug: s-side3 = " + tempAreaHolder3);

        double areaUnderRoot = s * tempAreaHolder1 * tempAreaHolder2 * tempAreaHolder3;
        System.out.println("Debug: areaUnderRoot = " + areaUnderRoot);

        double area;
        if (areaUnderRoot <= 0) {
            // Degenerate triangle or collinear points
            area = 0;
        } else {
            area = Math.sqrt(areaUnderRoot);
        }

        System.out.println("The area of the triangle is " + area);

        input.close();
    }
}
