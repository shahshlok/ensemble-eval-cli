import java.util.Scanner;

public class Q1 {
    public static void main(String[] args) {
        // Create a Scanner object to read user input from the keyboard
        Scanner userInputScanner = new Scanner(System.in);

        // Declare the starting velocity v0 and set an initial value
        double startingVelocityV0 = 0.0;

        // Declare the final velocity v1 and set an initial value
        double finalVelocityV1 = 0.0;

        // Declare the time t and set an initial value
        double timeElapsedT = 0.0;

        // Declare intermediate math variables to mirror the formula a = (v1 - v0) / t
        double aNumeratorDifference = finalVelocityV1 - startingVelocityV0; // v1 - v0
        double aDenominatorTime = timeElapsedT; // t

        // Compute the average acceleration using the formula
        double averageAccelerationA = aNumeratorDifference / aDenominatorTime;

        // Prompt the user to enter the starting velocity, final velocity, and time
        System.out.print("Enter v0, v1, and t: ");

        // Read the starting velocity v0 from the user
        startingVelocityV0 = userInputScanner.nextDouble();

        // Read the final velocity v1 from the user
        finalVelocityV1 = userInputScanner.nextDouble();

        // Read the time t from the user
        timeElapsedT = userInputScanner.nextDouble();

        // Output the result to the user
        System.out.println("The average acceleration is " + averageAccelerationA);

        // Close the scanner to free system resources
        userInputScanner.close();
    }
}