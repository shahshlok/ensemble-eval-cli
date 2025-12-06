import java.util.Scanner;

public class DistanceBetweenPoints {
    public static void main(String[] args) {
        // create a Scanner object for user input
        Scanner input = new Scanner(System.in);

        // declare variables for the first point (x1, y1)
        double x1;
        double y1;

        // declare variables for the second point (x2, y2)
        double x2;
        double y2;

        // prompt user for first point coordinates
        System.out.print("Enter x1 and y1: ");
        x1 = input.nextDouble(); // read x1
        y1 = input.nextDouble(); // read y1

        // prompt user for second point coordinates
        System.out.print("Enter x2 and y2: ");
        x2 = input.nextDouble(); // read x2
        y2 = input.nextDouble(); // read y2

        // compute the difference in x values
        double delta_x = x2 - x1;

        // compute the difference in y values
        double delta_y = y2 - y1;

        // compute the square of delta_x
        double delta_x_squared = delta_x * delta_x;

        // compute the square of delta_y
        double delta_y_squared = delta_y * delta_y;

        // compute the sum of squares
        double sum_of_squares = delta_x_squared + delta_y_squared;

        // compute the distance using Math.sqrt
        double distance = Math.sqrt(sum_of_squares);

        // display the result
        System.out.println("The distance of the two points is " + distance);

        // close the scanner
        input.close();
    }
}
