import java.util.Scanner;

public class AccelerationCalculator {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.print("Enter v0, v1, and t: ");
        double v0 = input.nextDouble();
        double v1 = input.nextDouble();
        double t = input.nextDouble();

        double acceleration = calculateAcceleration(v0, v1, t);

        System.out.println("The average acceleration is " + acceleration);
    }

    public static double calculateAcceleration(double v0, double v1, double t) {
        return (v1 - v0) / t;
    }
}
