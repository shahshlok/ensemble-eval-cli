import java.util.Scanner;

public class DistanceBetweenPoints {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        double x1 = readFirstX(input);
        double y1 = readFirstY(input);

        double x2 = readSecondX(input);
        double y2 = readSecondY(input);

        double distance = calculateDistance(x1, y1, x2, y2);

        System.out.println("The distance of the two points is " + distance);

        input.close();
    }

    public static double readFirstX(Scanner input) {
        System.out.print("Enter x1 and y1: ");
        return input.nextDouble();
    }

    public static double readFirstY(Scanner input) {
        return input.nextDouble();
    }

    public static double readSecondX(Scanner input) {
        System.out.print("Enter x2 and y2: ");
        return input.nextDouble();
    }

    public static double readSecondY(Scanner input) {
        return input.nextDouble();
    }

    public static double calculateDistance(double x1, double y1, double x2, double y2) {
        double xDifference = x2 - x1;
        double yDifference = y2 - y1;

        double xSquared = xDifference * xDifference;
        double ySquared = yDifference * yDifference;

        double sumOfSquares = xSquared + ySquared;

        double distance = Math.sqrt(sumOfSquares);
        return distance;
    }
}
