import java.util.Scanner;

public class Q4_TriangleAreaHeron {

    // This method will calculate the distance between two points (x1, y1) and (x2, y2)
    // using the distance formula from Question 3: sqrt( (x2-x1)^2 + (y2-y1)^2 )
    public static double calculate_distance(double x1, double y1, double x2, double y2) {
        // calculate the difference in x values
        double delta_x = x2 - x1;

        // calculate the difference in y values
        double delta_y = y2 - y1;

        // calculate the square of delta_x
        double delta_x_squared = delta_x * delta_x;

        // calculate the square of delta_y
        double delta_y_squared = delta_y * delta_y;

        // calculate the distance using the square root of the sum of squares
        double distance = Math.sqrt(delta_x_squared + delta_y_squared);

        // return the computed distance
        return distance;
    }

    public static void main(String[] args) {
        // create a Scanner object for reading user input from the console
        Scanner input = new Scanner(System.in);

        // declare variables for the coordinates of the three points
        double x1;
        double y1;
        double x2;
        double y2;
        double x3;
        double y3;

        // prompt the user for input: they should enter the three points of the triangle
        System.out.println("Enter three points for a triangle.");

        // prompt and read the first point (x1, y1)
        System.out.print("(x1, y1):");
        x1 = input.nextDouble();
        y1 = input.nextDouble();

        // prompt and read the second point (x2, y2)
        System.out.print("(x2, y2):");
        x2 = input.nextDouble();
        y2 = input.nextDouble();

        // prompt and read the third point (x3, y3)
        System.out.print("(x3, y3):");
        x3 = input.nextDouble();
        y3 = input.nextDouble();

        // calculate the length of each side of the triangle using the method from Q3

        // side1 is the distance between point 1 and point 2
        double side1 = calculate_distance(x1, y1, x2, y2);

        // side2 is the distance between point 2 and point 3
        double side2 = calculate_distance(x2, y2, x3, y3);

        // side3 is the distance between point 3 and point 1
        double side3 = calculate_distance(x3, y3, x1, y1);

        // now apply Heron's formula to compute the area of the triangle

        // compute the semi-perimeter s = (side1 + side2 + side3) / 2
        double s = (side1 + side2 + side3) / 2.0;

        // compute the area using the formula: sqrt( s * (s - side1) * (s - side2) * (s - side3) )
        double area = Math.sqrt(s * (s - side1) * (s - side2) * (s - side3));

        // display the result to the user
        System.out.println("The area of the triangle is " + area);

        // close the scanner to avoid resource leak
        input.close();
    }
}
