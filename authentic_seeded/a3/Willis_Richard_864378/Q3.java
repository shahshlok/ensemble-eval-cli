import java.util.Scanner;

public class DistanceBetweenPoints {
    public static void main(String[] args) {
        Scanner input_scanner = new Scanner(System.in);

        System.out.print("Enter x1 and y1: ");
        double x1 = input_scanner.nextDouble();
        double y_one = input_scanner.nextDouble();

        System.out.print("Enter x2 and y2: ");
        double x_two = input_scanner.nextDouble();
        double y2 = input_scanner.nextDouble();

        double dx_val = x_two - x1;
        double dyVal = y2 - y_one;

        double distance_between_points = Math.sqrt(dx_val * dx_val + dyVal * dyVal);

        System.out.println("The distance of the two points is " + distance_between_points);

        input_scanner.close();
    }
}
