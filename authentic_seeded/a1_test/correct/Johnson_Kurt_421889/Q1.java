import java.util.Scanner;

public class Q1 {
    public static void main(String[] args) {
        // Create a Scanner object so we can read user input from the keyboard
        Scanner userInputScanner = new Scanner(System.in);

        // Prompt the user to enter the starting velocity v0, final velocity v1, and time t
        System.out.print("Enter v0, v1, and t: ");

        // Read the three values from the user as doubles
        double startingVelocityV0 = userInputScanner.nextDouble();
        double finalVelocityV1 = userInputScanner.nextDouble();
        double timeIntervalT = userInputScanner.nextDouble();

        // Declare math variables to follow the acceleration formula a = (v1 - v0) / t
        double differenceInVelocity = finalVelocityV1 - startingVelocityV0;  // v1 - v0
        double timeDenominator = timeIntervalT;                              // t
        double averageAccelerationA = differenceInVelocity / timeDenominator; // a

        // Display the result to the user
        System.out.println("The average acceleration is " + averageAccelerationA);

        // Close the scanner to free resources
        userInputScanner.close();
    }
}