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

        // side1 between point1 and point2
        double temp1 = x2 - x1;
        double temp2 = y2 - y1;
        System.out.println("debug temp1 (x2-x1)=" + temp1 + " temp2 (y2-y1)=" + temp2);
        double side1 = Math.sqrt(temp1 * temp1 + temp2 * temp2);
        System.out.println("debug side1=" + side1);

        // side2 between point2 and point3
        double temp3 = x3 - x2;
        double temp4 = y3 - y2;
        System.out.println("debug temp3 (x3-x2)=" + temp3 + " temp4 (y3-y2)=" + temp4);
        double side2 = Math.sqrt(temp3 * temp3 + temp4 * temp4);
        System.out.println("debug side2=" + side2);

        // side3 between point3 and point1
        double temp5 = x1 - x3;
        double temp6 = y1 - y3;
        System.out.println("debug temp5 (x1-x3)=" + temp5 + " temp6 (y1-y3)=" + temp6);
        double side3 = Math.sqrt(temp5 * temp5 + temp6 * temp6);
        System.out.println("debug side3=" + side3);

        double sTemp1 = side1 + side2 + side3;
        System.out.println("debug sTemp1 (perimeter)=" + sTemp1);
        double s = sTemp1 / 2.0;
        System.out.println("debug s (semi-perimeter)=" + s);

        double part1 = s - side1;
        double part2 = s - side2;
        double part3 = s - side3;
        System.out.println("debug part1=" + part1 + " part2=" + part2 + " part3=" + part3);

        double areaTemp = s * part1 * part2 * part3;
        System.out.println("debug areaTemp before sqrt=" + areaTemp);

        double area = Math.sqrt(areaTemp);

        System.out.println("The area of the triangle is " + area);
    }
}
