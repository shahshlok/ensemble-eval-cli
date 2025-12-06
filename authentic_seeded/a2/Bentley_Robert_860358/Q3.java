import java.util.Scanner;

public class DistanceBetweenPoints {
    public static void main(String[] args) {
        // create a Scanner object to read user input from the keyboard
        Scanner input_scanner = new Scanner(System.in);

        // declare variables for the coordinates of the first point
        double x1;
        double y1;

        // declare variables for the coordinates of the second point
        double x2;
        double y2;

        // prompt the user to enter the coordinates of the first point
        System.out.print("Enter x1 and y1: ");
        x1 = input_scanner.nextDouble();
        y1 = input_scanner.nextDouble();

        // prompt the user to enter the coordinates of the second point
        System.out.print("Enter x2 and y2: ");
        x2 = input_scanner.nextDouble();
        y2 = input_scanner.nextDouble();

        // compute the difference in x-coordinates
        double delta_x = x2 - x1;

        // compute the difference in y-coordinates
        double delta_y = y2 - y1;

        // compute the square of the difference in x-coordinates
        double delta_x_squared = delta_x * delta_x;

        // compute the square of the difference in y-coordinates
        double delta_y_squared = delta_y * delta_y;

        // compute the sum of the squared differences
        double sum_of_squares = delta_x_squared + delta_y_squared;

        // compute the distance using the square root of the sum of squares
        double distance = Math.sqrt(sum_of_squares);

        // display the result to the user
        System.out.println("The distance of the two points is " + distance);

        // close the scanner to free system resources
        input_scanner.close();
    }
}
