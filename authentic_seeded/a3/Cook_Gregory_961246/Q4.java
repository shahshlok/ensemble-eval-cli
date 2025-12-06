import java.util.Scanner;

public class TriangleAreaQ4 {
    public static void main(String[] args) {

        // temp debug
        System.out.println("Starting TriangleAreaQ4 program...");

        Scanner input = new Scanner(System.in);

        System.out.println("Enter three points for a triangle.");

        System.out.print("(x1, y1):");
        double x1 = input.nextDouble();
        double y1 = input.nextDouble();
        System.out.println("Debug x1=" + x1 + " y1=" + y1);

        System.out.print("(x2, y2):");
        double x2 = input.nextDouble();
        double y2 = input.nextDouble();
        System.out.println("Debug x2=" + x2 + " y2=" + y2);

        System.out.print("(x3, y3):");
        double x3 = input.nextDouble();
        double y3 = input.nextDouble();
        System.out.println("Debug x3=" + x3 + " y3=" + y3);

        // Use Q3 logic: distance between two points
        double temp1 = x2 - x1;
        double temp2 = y2 - y1;
        double side1 = Math.sqrt(temp1 * temp1 + temp2 * temp2);
        System.out.println("Debug side1=" + side1);

        temp1 = x3 - x2;
        temp2 = y3 - y2;
        double side2 = Math.sqrt(temp1 * temp1 + temp2 * temp2);
        System.out.println("Debug side2=" + side2);

        temp1 = x1 - x3;
        temp2 = y1 - y3;
        double side3 = Math.sqrt(temp1 * temp1 + temp2 * temp2);
        System.out.println("Debug side3=" + side3);

        // Heron's formula
        double s = (side1 + side2 + side3) / 2.0;
        System.out.println("Debug s=" + s);

        double areaTemp1 = s - side1;
        double areaTemp2 = s - side2;
        double areaTemp3 = s - side3;
        System.out.println("Debug areaTemp1=" + areaTemp1 + " areaTemp2=" + areaTemp2 + " areaTemp3=" + areaTemp3);

        double area = Math.sqrt(s * areaTemp1 * areaTemp2 * areaTemp3);
        System.out.println("Debug raw area=" + area);

        // Intentional conceptual error (compile error)
        String row = System.out.println("*");

        System.out.println("The area of the triangle is " + area);
    }
}
