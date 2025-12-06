import java.util.Scanner;

public class Q1Acceleration {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        double v0 = 0;
        double v1 = 0;
        double t = 0;

        double a = computeAcceleration(v0, v1, t);

        promptAndReadInput(input);

        printResult(a);
    }

    public static double computeAcceleration(double v0, double v1, double t) {
        return (v1 - v0) / t;
    }

    public static void promptAndReadInput(Scanner input) {
        System.out.print("Enter v0, v1, and t: ");
        double v0 = input.nextDouble();
        double v1 = input.nextDouble();
        double t = input.nextDouble();
    }

    public static void printResult(double a) {
        System.out.println("The average acceleration is " + a);
    }
}
