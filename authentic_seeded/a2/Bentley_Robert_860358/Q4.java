import java.util.Scanner;

public class Q4_TriangleAreaHeron
{
    public static void main(String[] args)
    {
        // create scanner for user input
        Scanner keyboard = new Scanner(System.in);

        // explain to the user what to enter
        System.out.println("Enter three points for a triangle.");

        // declare variables for all x and y coordinates
        double x1;
        double y1;
        double x2;
        double y2;
        double x3;
        double y3;

        // ask for first point
        System.out.print("(x1, y1):");
        x1 = keyboard.nextDouble();
        y1 = keyboard.nextDouble();

        // ask for second point
        System.out.print("(x2, y2):");
        x2 = keyboard.nextDouble();
        y2 = keyboard.nextDouble();

        // ask for third point
        System.out.print("(x3, y3):");
        x3 = keyboard.nextDouble();
        y3 = keyboard.nextDouble();

        // calculate the lengths of the three sides using distance formula from Q3
        // side1 is between point1 and point2
        double side1 = Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));

        // side2 is between point2 and point3
        double side2 = Math.sqrt(Math.pow(x3 - x2, 2) + Math.pow(y3 - y2, 2));

        // side3 is between point1 and point3
        double side3 = Math.sqrt(Math.pow(x3 - x1, 2) + Math.pow(y3 - y1, 2));

        // calculate s using Heron's formula
        double s = (side1 + side2 + side3) / 2.0;

        // calculate the area using Heron's formula
        double area = Math.sqrt(s * (s - side1) * (s - side2) * (s - side3));

        // print the area of the triangle
        System.out.println("The area of the triangle is " + area);

        // close the scanner
        keyboard.close();
    }
}
