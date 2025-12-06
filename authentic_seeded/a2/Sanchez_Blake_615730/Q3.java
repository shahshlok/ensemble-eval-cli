import java.util.Scanner;

public class DistanceBetweenPoints {

    public static void main(String[] args) {

        // create a Scanner object to read input from the user
        Scanner input = new Scanner(System.in);

        // declare variables to store the coordinates of the two points
        double x1;
        double y1;
        double x2;
        double y2;

        // prompt the user for the first point's x and y values
        System.out.print("Enter x1 and y1: ");
        x1 = input.nextDouble(); // read x1
        y1 = input.nextDouble(); // read y1

        // prompt the user for the second point's x and y values
        System.out.print("Enter x2 and y2: ");
        x2 = input.nextDouble(); // read x2
        y2 = input.nextDouble(); // read y2

        // compute the difference in x values (x2 - x1)
        double x_difference = x2 - x1;

        // compute the difference in y values (y2 - y1)
        double y_difference = y2 - y1;

        // compute the square of the x difference
        double x_difference_squared = x_difference * x_difference;

        // compute the square of the y difference
        double y_difference_squared = y_difference * y_difference;

        // compute the sum of the squares
        double sum_of_squares = x_difference_squared + y_difference_squared;

        // compute the distance using the square root of the sum of the squares
        double distance = Math.sqrt(sum_of_squares);

        // display the result to the user
        System.out.println("The distance of the two points is " + distance);

        // close the Scanner object to free resources
        input.close();
    }
}
