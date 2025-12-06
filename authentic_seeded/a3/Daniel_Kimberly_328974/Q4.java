import java.util.Scanner;

public class Q4_TriangleArea_HeronsFormula {

    public static void main(String[] args) {

        // create scanner for user input
        Scanner input = new Scanner(System.in);

        // verbose comments and snake_case variable names
        // declare variables for the coordinates
        double x1, y1;
        double x2, y2;
        double x3, y3;

        // prompt user for the three points
        System.out.println("Enter three points for a triangle.");

        // first point input
        System.out.print("(x1, y1):");
        x1 = input.nextDouble();
        y1 = input.nextDouble();

        // second point input
        System.out.print("(x2, y2):");
        x2 = input.nextDouble();
        y2 = input.nextDouble();

        // third point input
        System.out.print("(x3, y3):");
        x3 = input.nextDouble();
        y3 = input.nextDouble();

        // calculate the lengths of the three sides using distance formula (Q3 logic)
        // side1 is distance between point1(x1,y1) and point2(x2,y2)
        double side1 = Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));

        // side2 is distance between point2(x2,y2) and point3(x3,y3)
        double side2 = Math.sqrt(Math.pow(x3 - x2, 2) + Math.pow(y3 - y2, 2));

        // side3 is distance between point3(x3,y3) and point1(x1,y1)
        double side3 = Math.sqrt(Math.pow(x1 - x3, 2) + Math.pow(y1 - y3, 2));

        // calculate s using Heron's formula: s = (side1 + side2 + side3) / 2
        double s = (side1 + side2 + side3) / 2.0;

        // calculate area using Heron's formula:
        // area = sqrt( s * (s - side1) * (s - side2) * (s - side3) )
        double area = Math.sqrt(s * (s - side1) * (s - side2) * (s - side3));

        // print the area of the triangle
        System.out.println("The area of the triangle is " + area);

        // --------------------------------------------------------------------
        // The following part is intentionally wrong based on the "conceptual error"
        // we are supposed to include.
        // I want to print a star and then check if the "star" is '*',
        // but I treat a String like a char on purpose.
        // --------------------------------------------------------------------

        // print a star using System.out.print("*");
        System.out.print("*");

        // I think star is a String that holds "*"
        String star = "*";

        // I will compare the String star to '*' like it is a char (this is the required error)
        if (star == '*') {   // <-- intentional type / comparison error
            System.out.println("Star is a star!");
        }

        // close scanner
        input.close();
    }
}
