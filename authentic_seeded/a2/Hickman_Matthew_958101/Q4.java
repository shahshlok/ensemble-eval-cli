import java.util.Scanner;

public class Q4TriangleArea {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.println("Enter three points for a triangle.");

        System.out.print("(x1, y1):");
        double x1 = input.nextDouble();
        double y1 = input.nextDouble();
        System.out.println("debug x1=" + x1 + " y1=" + y1);

        System.out.print("(x2, y2):");
        double x2 = input.nextDouble();
        double y2 = input.nextDouble();
        System.out.println("debug x2=" + x2 + " y2=" + y2);

        System.out.print("(x3, y3):");
        double x3 = input.nextDouble();
        double y3 = input.nextDouble();
        System.out.println("debug x3=" + x3 + " y3=" + y3);

        double temp1 = x2 - x1;
        double temp2 = y2 - y1;
        System.out.println("debug temp1 for side1=" + temp1 + " temp2=" + temp2);
        double side1 = Math.sqrt(temp1 * temp1 + temp2 * temp2);
        System.out.println("debug side1=" + side1);

        temp1 = x3 - x2;
        temp2 = y3 - y2;
        System.out.println("debug temp1 for side2=" + temp1 + " temp2=" + temp2);
        double side2 = Math.sqrt(temp1 * temp1 + temp2 * temp2);
        System.out.println("debug side2=" + side2);

        temp1 = x3 - x1;
        temp2 = y3 - y1;
        System.out.println("debug temp1 for side3=" + temp1 + " temp2=" + temp2);
        double side3 = Math.sqrt(temp1 * temp1 + temp2 * temp2);
        System.out.println("debug side3=" + side3);

        double s = (side1 + side2 + side3) / 2.0;
        System.out.println("debug s=" + s);

        double tempArea1 = s - side1;
        double tempArea2 = s - side2;
        double tempArea3 = s - side3;
        System.out.println("debug tempArea1=" + tempArea1 + " tempArea2=" + tempArea2 + " tempArea3=" + tempArea3);

        double areaUnderRoot = s * tempArea1 * tempArea2 * tempArea3;
        System.out.println("debug areaUnderRoot=" + areaUnderRoot);

        double area;
        if (areaUnderRoot <= 0) {
            area = 0;
        } else {
            area = Math.sqrt(areaUnderRoot);
        }

        System.out.println("The area of the triangle is " + area);

        input.close();
    }
}
