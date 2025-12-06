import java.util.Scanner;

public class DistanceBetweenPoints {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        double x1 = readFirstX(input);
        double y1 = readFirstY(input);

        double x2 = readSecondX(input);
        double y2 = readSecondY(input);

        double distance = computeDistance(x1, y1, x2, y2);

        System.out.println("The distance of the two points is " + distance);
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

    public static double computeDistance(double x1, double y1, double x2, double y2) {
        double xDiff = computeDifference(x2, x1);
        double yDiff = computeDifference(y2, y1);

        double xDiffSquared = square(xDiff);
        double yDiffSquared = square(yDiff);

        double sum = xDiffSquared + yDiffSquared;

        return Math.sqrt(sum);
    }

    public static double computeDifference(double a, double b) {
        return a - b;
    }

    public static double square(double value) {
        return value * value;
    }
}
