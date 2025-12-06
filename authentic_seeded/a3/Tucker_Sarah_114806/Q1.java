import java.util.Scanner;

public class AccelerationCalculator {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        // TODO ask the user for starting velocity, final velocity, and time
        System.out.print("Enter v0, v1, and t: ");

        double v0 = input.nextDouble();
        double v1 = input.nextDouble();
        double t = input.nextDouble();

        // maybe we should check t != 0, but instructions don't say so
        double acceleration = (v1 - v0) / t;

        System.out.println("The average acceleration is " + acceleration);

        input.close();
    }
}
