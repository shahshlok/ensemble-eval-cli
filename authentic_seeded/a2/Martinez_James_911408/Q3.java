import java.util.Scanner;

public class DistanceBetweenTwoPoints {

    public static void main(String[] args) {

        // Create a Scanner object to read input from the user
        Scanner input = new Scanner(System.in);

        // Declare variables for the coordinates of the two points
        double x1;
        double y1;
        double x2;
        double y2;

        // Prompt the user for the first point (x1, y1)
        System.out.print("Enter x1 and y1: ");
        x1 = input.nextDouble();  // Read x1 from user
        y1 = input.nextDouble();  // Read y1 from user

        // Prompt the user for the second point (x2, y2)
        System.out.print("Enter x2 and y2: ");
        x2 = input.nextDouble();  // Read x2 from user
        y2 = input.nextDouble();  // Read y2 from user

        // Compute the difference in x-coordinates
        double x_difference = x2 - x1;

        // Compute the difference in y-coordinates
        double y_difference = y2 - y1;

        // Compute the square of the x difference
        double x_difference_squared = x_difference * x_difference;

        // Compute the square of the y difference
        double y_difference_squared = y_difference * y_difference;

        // Compute the sum of the squared differences
        double sum_of_squares = x_difference_squared + y_difference_squared;

        // Compute the distance using the square root of the sum of squares
        double distance = Math.sqrt(sum_of_squares);

        // Display the distance between the two points
        System.out.println("The distance of the two points is " + distance);

        // Close the scanner to free up resources
        input.close();
    }
}
