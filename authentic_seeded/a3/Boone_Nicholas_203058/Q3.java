import java.util.Scanner;

public class DistanceBetweenPoints {

    public static void main(String[] args) {

        // create a Scanner object to read from the keyboard
        Scanner input = new Scanner(System.in);

        // declare variables to store the coordinates
        double x1;
        double y1;
        double x2;
        double y2;

        // prompt the user to enter the first point (x1 and y1)
        System.out.print("Enter x1 and y1: ");
        x1 = input.nextDouble(); // read x1 from the user
        y1 = input.nextDouble(); // read y1 from the user

        // prompt the user to enter the second point (x2 and y2)
        System.out.print("Enter x2 and y2: ");
        x2 = input.nextDouble(); // read x2 from the user
        y2 = input.nextDouble(); // read y2 from the user

        // compute the differences in x and y coordinates
        double x_difference = x2 - x1; // (x2 - x1)
        double y_difference = y2 - y1; // (y2 - y1)

        // compute the squares of the differences
        double x_difference_squared = x_difference * x_difference; // (x2 - x1)^2
        double y_difference_squared = y_difference * y_difference; // (y2 - y1)^2

        // compute the sum of the squares
        double sum_of_squares = x_difference_squared + y_difference_squared;

        // compute the distance using Math.sqrt
        double distance = Math.sqrt(sum_of_squares);

        // display the result to the user
        System.out.println("The distance of the two points is " + distance);

        // close the Scanner to avoid resource leaks
        input.close();
    }
}
