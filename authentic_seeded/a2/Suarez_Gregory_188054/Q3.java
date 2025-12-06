import java.util.Scanner;

public class DistanceBetweenPointsQ3 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        double x;
        double y;

        System.out.print("Enter x1 and y1: ");
        x = input.nextDouble();
        y = input.nextDouble();

        System.out.print("Enter x2 and y2: ");
        x = input.nextDouble();
        y = input.nextDouble();

        double xDifference = x - x;
        double yDifference = y - y;

        double xDifferenceSquared = xDifference * xDifference;
        double yDifferenceSquared = yDifference * yDifference;

        double sumOfSquares = xDifferenceSquared + yDifferenceSquared;

        double distance = Math.sqrt(sumOfSquares);

        System.out.println("The distance of the two points is " + distance);
    }
}
