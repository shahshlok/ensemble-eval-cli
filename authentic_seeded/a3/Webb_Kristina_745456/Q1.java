import java.util.Scanner;

public class AccelerationCalculator {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        // TODO: maybe explain to user what's going on?
        System.out.print("Enter v0, v1, and t: ");

        double v0 = input.nextDouble();
        double v1 = input.nextDouble();
        double t = input.nextDouble();

        // TODO: what if t is zero? (ignore for now)
        double a = (v1 - v0) / t;

        System.out.println("The average acceleration is " + a);

        input.close();
    }
}
