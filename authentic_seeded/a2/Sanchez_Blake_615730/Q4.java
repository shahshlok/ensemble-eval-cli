import java.util.Scanner;

public class Q4_TriangleArea
{
    // Method to calculate the distance between two points using Question 3 logic
    public static double calculate_distance(double x1, double y1, double x2, double y2)
    {
        // Calculate the difference in x values
        double delta_x = x2 - x1;

        // Calculate the difference in y values
        double delta_y = y2 - y1;

        // Calculate the distance using the distance formula
        double distance = Math.sqrt(delta_x * delta_x + delta_y * delta_y);

        // Return the calculated distance
        return distance;
    }

    public static void main(String[] args)
    {
        // Create a Scanner object to read input from the user
        Scanner input = new Scanner(System.in);

        // Declare variables for the three points of the triangle
        double x1;
        double y1;
        double x2;
        double y2;
        double x3;
        double y3;

        // Prompt the user to enter three points for a triangle
        System.out.println("Enter three points for a triangle.");

        // Prompt for the first point (x1, y1)
        System.out.print("(x1, y1):");
        x1 = input.nextDouble();
        y1 = input.nextDouble();

        // Prompt for the second point (x2, y2)
        System.out.print("(x2, y2):");
        x2 = input.nextDouble();
        y2 = input.nextDouble();

        // Prompt for the third point (x3, y3)
        System.out.print("(x3, y3):");
        x3 = input.nextDouble();
        y3 = input.nextDouble();

        // Calculate the length of each side of the triangle using the distance method
        double side1 = calculate_distance(x1, y1, x2, y2); // distance between point 1 and point 2
        double side2 = calculate_distance(x2, y2, x3, y3); // distance between point 2 and point 3
        double side3 = calculate_distance(x3, y3, x1, y1); // distance between point 3 and point 1

        // Calculate the semi-perimeter (s) of the triangle using Heron's formula
        double s = (side1 + side2 + side3) / 2.0;

        // Calculate the area of the triangle using Heron's formula
        double area = Math.sqrt(s * (s - side1) * (s - side2) * (s - side3));

        // Display the area of the triangle to the user
        System.out.println("The area of the triangle is " + area);

        // Close the Scanner object
        input.close();
    }
}
