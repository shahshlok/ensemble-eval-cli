import java.util.Scanner;

public class TriangleAreaHeron {

    public static void main(String[] args) {

        Scanner userInputScanner = new Scanner(System.in);

        System.out.println("Enter three points for a triangle.");

        System.out.print("(x1, y1): ");
        double x1 = userInputScanner.nextDouble();
        double y1 = userInputScanner.nextDouble();

        System.out.print("(x2, y2): ");
        double x2 = userInputScanner.nextDouble();
        double y2 = userInputScanner.nextDouble();

        System.out.print("(x3, y3): ");
        double x3 = userInputScanner.nextDouble();
        double y3 = userInputScanner.nextDouble();


        double side1 = calculateDistance(x1, y1, x2, y2);
        double side2 = calculateDistance(x2, y2, x3, y3);
        double side3 = calculateDistance(x3, y3, x1, y1);


        double semiPerimeter = (side1 + side2 + side3) / 2.0;

        double triangleArea = Math.sqrt(
                semiPerimeter
                        * (semiPerimeter - side1)
                        * (semiPerimeter - side2)
                        * (semiPerimeter - side3)
        );


        System.out.println("The area of the triangle is " + triangleArea);

        userInputScanner.close();
    }


    public static double calculateDistance(double startX, double startY, double endX, double endY) {

        double xDifference = endX - startX;
        double yDifference = endY - startY;

        double distance = Math.sqrt(xDifference * xDifference + yDifference * yDifference);

        return distance;
    }

}
