import java.util.Scanner;

public class DistanceBetweenPoints {

    public static void main(String[] args) {

        // create scanner for user input
        Scanner input_scanner = new Scanner(System.in);

        // declare variables for the coordinates of the two points
        double x1;
        double y1;
        double x2;
        double y2;

        // prompt the user for the first point
        System.out.print("Enter x1 and y1: ");
        x1 = input_scanner.nextDouble();
        y1 = input_scanner.nextDouble();

        // prompt the user for the second point
        System.out.print("Enter x2 and y2: ");
        x2 = input_scanner.nextDouble();
        y2 = input_scanner.nextDouble();

        // compute the difference in x and y values
        double delta_x = x2 - x1;
        double delta_y = y2 - y1;

        // compute the squared differences
        double delta_x_squared = delta_x * delta_x;
        double delta_y_squared = delta_y * delta_y;

        // compute the sum of the squares
        double sum_of_squares = delta_x_squared + delta_y_squared;

        // compute the distance using the square root of the sum of squares
        double distance = Math.sqrt(sum_of_squares);

        // display the result to the user
        System.out.println("The distance of the two points is " + distance);

        // ------------------------------------------------------
        // below is some grade checking logic that I am practicing
        // it is completely independent from the distance problem
        // ------------------------------------------------------

        double grade = 95.0;

        if (grade >= 90) System.out.println("A");
        if (grade >= 80) System.out.println("B");
        if (grade >= 70) System.out.println("C");
        if (grade >= 60) System.out.println("D");
        if (grade < 60) System.out.println("F");

        // close the scanner
        input_scanner.close();
    }
}
