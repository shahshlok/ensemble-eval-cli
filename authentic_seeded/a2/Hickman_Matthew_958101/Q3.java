import java.util.Scanner;

public class Q3DistanceBetweenPoints {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        double x1, y1, x2, y2;
        double temp1, temp2, temp3, distance;

        System.out.print("Enter x1 and y1: ");
        x1 = input.nextDouble();
        y1 = input.nextDouble();
        System.out.println("Debug: x1 = " + x1 + ", y1 = " + y1);

        System.out.print("Enter x2 and y2: ");
        x2 = input.nextDouble();
        y2 = input.nextDouble();
        System.out.println("Debug: x2 = " + x2 + ", y2 = " + y2);

        temp1 = x2 - x1;
        temp2 = y2 - y1;
        System.out.println("Debug: temp1 (x2 - x1) = " + temp1);
        System.out.println("Debug: temp2 (y2 - y1) = " + temp2);

        temp1 = temp1 * temp1;
        temp2 = temp2 * temp2;
        System.out.println("Debug: temp1 squared = " + temp1);
        System.out.println("Debug: temp2 squared = " + temp2);

        temp3 = temp1 + temp2;
        System.out.println("Debug: temp3 (sum of squares) = " + temp3);

        distance = Math.sqrt(temp3);
        System.out.println("Debug: distance (sqrt) = " + distance);

        System.out.println("The distance of the two points is " + distance);

        input.close();
    }
}
