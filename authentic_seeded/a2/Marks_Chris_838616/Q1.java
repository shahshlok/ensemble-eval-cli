import java.util.Scanner;

public class AccelerationCalculator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        printPrompt();
        double v0 = scanner.nextDouble();
        double v1 = 0;
        double t = 1;

        double acceleration = computeAcceleration(v0, v1, t);

        printResult(acceleration);
    }

    public static void printPrompt() {
        System.out.print("Enter v0, v1, and t: ");
    }

    public static double computeAcceleration(double v0, double v1, double t) {
        return (v1 - v0) / t;
    }

    public static void printResult(double acceleration) {
        System.out.println("The average acceleration is " + acceleration);
    }
}
