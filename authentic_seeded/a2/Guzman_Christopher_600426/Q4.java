import java.util.Scanner;

public class Q4TriangleArea {
    public static void main(String[] args) {
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

        // Using Q3-style distance calculation
        double temp1 = x2 - x1;
        double temp2 = y2 - y1;
        System.out.println("Debug temp1=" + temp1 + " temp2=" + temp2);
        double side1 = Math.sqrt(temp1 * temp1 + temp2 * temp2);
        System.out.println("Debug side1=" + side1);

        temp1 = x3 - x2;
        temp2 = y3 - y2;
        System.out.println("Debug temp1=" + temp1 + " temp2=" + temp2);
        double side2 = Math.sqrt(temp1 * temp1 + temp2 * temp2);
        System.out.println("Debug side2=" + side2);

        temp1 = x3 - x1;
        temp2 = y3 - y1;
        System.out.println("Debug temp1=" + temp1 + " temp2=" + temp2);
        double side3 = Math.sqrt(temp1 * temp1 + temp2 * temp2);
        System.out.println("Debug side3=" + side3);

        double s = (side1 + side2 + side3) / 2.0;
        System.out.println("Debug s=" + s);

        double tempArea1 = s - side1;
        double tempArea2 = s - side2;
        double tempArea3 = s - side3;
        System.out.println("Debug tempArea1=" + tempArea1 + " tempArea2=" + tempArea2 + " tempArea3=" + tempArea3);

        double areaInside = s * tempArea1 * tempArea2 * tempArea3;
        System.out.println("Debug areaInside=" + areaInside);

        double area = Math.sqrt(areaInside);

        System.out.println("The area of the triangle is " + area);
    }
}
