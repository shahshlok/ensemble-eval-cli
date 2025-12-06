import java.util.Scanner;

public class Q1 {
    public static void main(String[] args) {
        // Create a Scanner object to read user input from the keyboard
        Scanner userInputScanner = new Scanner(System.in);

        // Prompt the user to enter the starting velocity, final velocity, and time
        System.out.print("Enter v0, v1, and t: ");

        // Read the starting velocity v0 from the user
        double startingVelocityV0 = userInputScanner.nextDouble();

        // Read the final velocity v1 from the user
        double finalVelocityV1 = userInputScanner.nextDouble();

        // Read the time t from the user
        double timeElapsedT = userInputScanner.nextDouble();

        // Declare intermediate math variables to mirror the formula a = (v1 - v0) / t
        double aNumeratorDifference = finalVelocityV1 - startingVelocityV0; // v1 - v0
        double aDenominatorTime = timeElapsedT; // t

        // Compute the average acceleration using the formula
        double averageAccelerationA = aNumeratorDifference / aDenominatorTime;

        // Output the result to the user
        System.out.println("The average acceleration is " + averageAccelerationA);

        // Close the scanner to free system resources
        userInputScanner.close();
    }
}