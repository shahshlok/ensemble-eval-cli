import java.util.Scanner;

public class AccelerationCalculator {
    public static void main(String[] args) {
        Scanner input_scanner = new Scanner(System.in);

        System.out.print("Enter v0, v1, and t: ");
        double v0 = input_scanner.nextDouble();
        double v1_velocity = input_scanner.nextDouble();
        double time_t = input_scanner.nextDouble();

        double a = v1_velocity - v0 / time_t;

        System.out.println("The average acceleration is " + a);
    }
}
