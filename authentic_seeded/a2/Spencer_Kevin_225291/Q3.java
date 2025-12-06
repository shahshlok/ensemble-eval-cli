import java.util.Scanner;

public class DistanceBetweenTwoPoints {

    public static void main(String[] args) {

        Scanner inputScanner = new Scanner(System.in);

        System.out.print("Enter x1 and y1: ");
        double x1Coordinate = inputScanner.nextDouble();
        double y1Coordinate = inputScanner.nextDouble();

        System.out.print("Enter x2 and y2: ");
        double x2Coordinate = inputScanner.nextDouble();
        double y2Coordinate = inputScanner.nextDouble();

        double xDifference = x2Coordinate - x1Coordinate;
        double yDifference = y2Coordinate - y1Coordinate;

        double distanceBetweenPoints = Math.sqrt(xDifference * xDifference + yDifference * yDifference);

        System.out.println("The distance of the two points is " + distanceBetweenPoints);

        inputScanner.close();
    }
}
