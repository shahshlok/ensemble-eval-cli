import java.util.Scanner;

public class AccelerationCalculatorQ1 {

    public static void main(String[] args) {

        // Create a Scanner object to read input from the user
        Scanner input = new Scanner(System.in);

        // Prompt the user to enter the starting velocity (v0),
        // the final velocity (v1), and the time (t)
        System.out.print("Enter v0, v1, and t: ");

        // Read the starting velocity as a double
        double v0 = input.nextDouble();

        // Read the final velocity as a double
        double v1 = input.nextDouble();

        // Read the time as a double
        double t = input.nextDouble();

        // Compute the average acceleration using the formula:
        // a = (v1 - v0) / t
        double average_acceleration = (v1 - v0) / t;

        // Display the result to the user
        System.out.println("The average acceleration is " + average_acceleration);

        // Close the scanner to free system resources
        input.close();
    }
}
