import java.util.Scanner;

public class DistanceTwoPoints {
    public static void main(String[] args) {
        Scanner input_reader = new Scanner(System.in);

        System.out.print("Enter x1 and y1: ");
        double x1 = input_reader.nextDouble();
        double y_one = input_reader.nextDouble();

        System.out.print("Enter x2 and y2: ");
        double x_two = input_reader.nextDouble();
        double y2 = input_reader.nextDouble();

        double xDiff = x_two - x1;
        double y_diff = y2 - y_one;

        double distance = Math.sqrt(xDiff * xDiff + y_diff * y_diff);

        System.out.println("The distance of the two points is " + distance);
    }
}
