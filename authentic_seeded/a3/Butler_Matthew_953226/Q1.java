import java.util.Scanner;

public class AccelerationCalculator {
    public static void main(String[] args) {
        Scanner input_reader = new Scanner(System.in);

        System.out.print("Enter v0, v1, and t: ");
        double v0 = input_reader.nextDouble();
        double finalVelocity = input_reader.nextDouble();
        double t_time = input_reader.nextDouble();

        double average_accel = (finalVelocity - v0) / t_time;

        System.out.println("The average acceleration is " + average_accel);
    }
}
