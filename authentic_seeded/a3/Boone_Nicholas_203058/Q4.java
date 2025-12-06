import java.util.Scanner;

public class TriangleAreaHeron {
    
    // main method
    public static void main(String[] args) {
        
        // create a Scanner object to read user input
        Scanner input = new Scanner(System.in);
        
        // declare variables for all coordinates of the three points
        double x1, y1, x2, y2, x3, y3;
        
        // ask user to enter the three points of the triangle
        System.out.println("Enter three points for a triangle.");
        
        // prompt and input for first point (x1, y1)
        System.out.print("(x1, y1):");
        x1 = input.nextDouble();
        y1 = input.nextDouble();
        
        // prompt and input for second point (x2, y2)
        System.out.print("(x2, y2):");
        x2 = input.nextDouble();
        y2 = input.nextDouble();
        
        // prompt and input for third point (x3, y3)
        System.out.print("(x3, y3):");
        x3 = input.nextDouble();
        y3 = input.nextDouble();
        
        // calculate the length of each side using the distance formula from Q3
        // side1 is the distance between point1 (x1, y1) and point2 (x2, y2)
        double side1 = Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
        
        // side2 is the distance between point2 (x2, y2) and point3 (x3, y3)
        double side2 = Math.sqrt(Math.pow(x3 - x2, 2) + Math.pow(y3 - y2, 2));
        
        // side3 is the distance between point3 (x3, y3) and point1 (x1, y1)
        double side3 = Math.sqrt(Math.pow(x1 - x3, 2) + Math.pow(y1 - y3, 2));
        
        // calculate semi-perimeter s using Heron's formula: s = (side1 + side2 + side3) / 2
        double s = (side1 + side2 + side3) / 2.0;
        
        // calculate the area using Heron's formula:
        // area = sqrt(s * (s - side1) * (s - side2) * (s - side3))
        double area = Math.sqrt(s * (s - side1) * (s - side2) * (s - side3));
        
        // display the area of the triangle
        System.out.println("The area of the triangle is " + area);
        
        // close the Scanner
        input.close();
    }
}
