import java.util.Scanner;

public class TriangleAreaHeron {

    public static void main(String[] args) {

        Scanner inputScanner = new Scanner(System.in);


        System.out.println("Enter three points for a triangle.");

        System.out.print("(x1, y1): ");
        double x1 = inputScanner.nextDouble();
        double y1 = inputScanner.nextDouble();

        System.out.print("(x2, y2): ");
        double x2 = inputScanner.nextDouble();
        double y2 = inputScanner.nextDouble();

        System.out.print("(x3, y3): ");
        double x3 = inputScanner.nextDouble();
        double y3 = inputScanner.nextDouble();


        double side1 = calculateDistance(x1, y1, x2, y2);
        double side2 = calculateDistance(x2, y2, x3, y3);
        double side3 = calculateDistance(x3, y3, x1, y1);


        double semiPerimeter = (side1 + side2 + side3) / 2.0;

        double area = Math.sqrt(semiPerimeter
                * (semiPerimeter - side1)
                * (semiPerimeter - side2)
                * (semiPerimeter - side3));


        System.out.println("The area of the triangle is " + area);


        inputScanner.close();
    }


    public static double calculateDistance(double xStart, double yStart, double xEnd, double yEnd) {

        double distanceX = xEnd - xStart;
        double distanceY = yEnd - yStart;

        double distance = Math.sqrt(distanceX * distanceX + distanceY * distanceY);

        return distance;
    }

}
