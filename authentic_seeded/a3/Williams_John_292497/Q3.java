import java.util.Scanner;

public class DistanceBetweenPoints {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.print("Enter x1 and y1: ");
        double x1 = input.nextDouble();
        double y1 = input.nextDouble();

        System.out.print("Enter x2 and y2: ");
        double x2 = input.nextDouble();
        double y2 = input.nextDouble();

        double xDifference = x2 - x1;
        double yDifference = y2 - y1;

        double xDifferenceSquared = xDifference * xDifference;
        double yDifferenceSquared = yDifference * yDifference;

        double sumOfSquares = xDifferenceSquared + yDifferenceSquared;

        double distance = Math.sqrt(sumOfSquares);

        System.out.println("The distance of the two points is " + distance);

        input.close();
    }
}
