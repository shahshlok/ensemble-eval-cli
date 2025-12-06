import java.util.Scanner;

public class AccelerationCalculator {

    public static void main(String[] args) {

        // Create a scanner object to read input from the user
        Scanner input = new Scanner(System.in);

        // Prompt the user to enter v0, v1, and t
        System.out.print("Enter v0, v1, and t: ");

        // Read the starting velocity v0 from the user
        double v0 = input.nextDouble();

        // Read the final velocity v1 from the user
        double v1 = input.nextDouble();

        // Read the time t from the user
        double t = input.nextDouble();

        // Compute the average acceleration using the formula (v1 - v0) / t
        double average_acceleration = (v1 - v0) / t;

        // Display the result to the user
        System.out.println("The average acceleration is " + average_acceleration);

        // Close the scanner to avoid resource leak
        input.close();
    }
}
