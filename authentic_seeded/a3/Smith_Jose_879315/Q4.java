import java.util.Scanner;

public class Q4_TriangleArea
{
    // main method where the program starts
    public static void main(String[] args)
    {
        // create a Scanner object for user input
        Scanner input = new Scanner(System.in);

        // declare variables for the coordinates of the three points
        double x1, y1, x2, y2, x3, y3;

        // prompt the user to enter the three points of a triangle
        System.out.println("Enter three points for a triangle.");

        // prompt and read first point
        System.out.print("(x1, y1):");
        x1 = input.nextDouble();
        y1 = input.nextDouble();

        // prompt and read second point
        System.out.print("(x2, y2):");
        x2 = input.nextDouble();
        y2 = input.nextDouble();

        // prompt and read third point
        System.out.print("(x3, y3):");
        x3 = input.nextDouble();
        y3 = input.nextDouble();

        // calculate the lengths of the three sides using distance formula
        // side1 is distance between point1 (x1, y1) and point2 (x2, y2)
        double side1 = distance_between_points(x1, y1, x2, y2);

        // side2 is distance between point2 (x2, y2) and point3 (x3, y3)
        double side2 = distance_between_points(x2, y2, x3, y3);

        // side3 is distance between point3 (x3, y3) and point1 (x1, y1)
        double side3 = distance_between_points(x3, y3, x1, y1);

        // calculate the semi-perimeter s using the formula: s = (side1 + side2 + side3) / 2
        double s = (side1 + side2 + side3) / 2.0;

        // calculate the area of the triangle using Heron's formula:
        // area = sqrt( s * (s - side1) * (s - side2) * (s - side3) )
        double area = Math.sqrt(s * (s - side1) * (s - side2) * (s - side3));

        // display the area of the triangle
        System.out.println("The area of the triangle is " + area);

        // close the scanner
        input.close();
    }

    // method to calculate the distance between two points (xA, yA) and (xB, yB)
    public static double distance_between_points(double x_a, double y_a, double x_b, double y_b)
    {
        // calculate the difference in x coordinates
        double delta_x = x_b - x_a;

        // calculate the difference in y coordinates
        double delta_y = y_b - y_a;

        // calculate the distance using the distance formula:
        // distance = sqrt( (delta_x)^2 + (delta_y)^2 )
        double distance = Math.sqrt(delta_x * delta_x + delta_y * delta_y);

        // return the calculated distance
        return distance;
    }
}
