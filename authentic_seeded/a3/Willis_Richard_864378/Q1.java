import java.util.Scanner;

public class AccelerationCalculator {

    public static void main(String[] args) {
        Scanner input_reader = new Scanner(System.in);

        System.out.print("Enter v0, v1, and t: ");
        double v0 = input_reader.nextDouble();
        double finalVelocity = input_reader.nextDouble();
        double t_val = input_reader.nextDouble();

        double avg_acceleration = (finalVelocity - v0) / t_val;

        System.out.println("The average acceleration is " + avg_acceleration);
    }
}
