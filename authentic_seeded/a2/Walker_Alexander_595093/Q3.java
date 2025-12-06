import java.util.Scanner;

public class Q3DistanceBetweenPoints {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.print("Enter x1 and y1: ");
        double x1 = input.nextDouble();
        double y1 = input.nextDouble();
        System.out.println("Debug: x1=" + x1 + " y1=" + y1);

        System.out.print("Enter x2 and y2: ");
        double x2 = input.nextDouble();
        double y2 = input.nextDouble();
        System.out.println("Debug: x2=" + x2 + " y2=" + y2);

        double temp1 = x2 - x1;
        double temp2 = y2 - y1;
        System.out.println("Debug: temp1 (x2-x1)=" + temp1);
        System.out.println("Debug: temp2 (y2-y1)=" + temp2);

        double temp3 = temp1 * temp1;
        double temp4 = temp2 * temp2;
        System.out.println("Debug: temp3 (temp1^2)=" + temp3);
        System.out.println("Debug: temp4 (temp2^2)=" + temp4);

        double temp5 = temp3 + temp4;
        System.out.println("Debug: temp5 (sum squares)=" + temp5);

        double distance = Math.sqrt(temp5);
        System.out.println("Debug: distance=" + distance);

        System.out.println("The distance of the two points is " + distance);

        input.close();
    }
}
