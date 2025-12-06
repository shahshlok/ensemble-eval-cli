import java.util.Scanner;

public class AccelerationCalculator {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        double v0 = readDouble(input, "Enter v0, v1, and t: ");
        double v1 = input.nextDouble();
        double t = input.nextDouble();

        double acceleration = computeAcceleration(v0, v1, t);

        System.out.println("The average acceleration is " + acceleration);
    }

    public static double computeAcceleration(double v0, double v1, double t) {
        return (v1 - v0) / t;
    }

    public static double readDouble(Scanner input, String prompt) {
        System.out.print(prompt);
        return input.nextDouble();
    }
}
