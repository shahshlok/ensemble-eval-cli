import java.util.Scanner;

public class Q2 {
    public static void main(String[] args) {
        // Create a Scanner object to read input from the user
        Scanner userInputScanner = new Scanner(System.in);

        // Define initial variables for velocity and time so the formula is ready
        double initialVelocityV0 = 0.0;
        double finalVelocityV1 = 0.0;
        double timeIntervalT = 1.0;

        // Define acceleration using the formula a = (v1 - v0) / t
        double accelerationValue = (finalVelocityV1 - initialVelocityV0) / timeIntervalT;

        // Prompt the user to enter the driving distance in miles
        System.out.print("Enter the driving distance in miles: ");
        double drivingDistanceInMiles = userInputScanner.nextDouble();

        // Prompt the user to enter miles per gallon
        System.out.print("Enter miles per gallon: ");
        double milesPerGallonValue = userInputScanner.nextDouble();

        // Prompt the user to enter price per gallon in dollars
        System.out.print("Enter price in $ per gallon: ");
        double pricePerGallonInDollars = userInputScanner.nextDouble();

        // Extra nervous check: just read some values for velocity and time from the user
        System.out.print("Enter initial velocity v0 in m/s: ");
        initialVelocityV0 = userInputScanner.nextDouble();

        System.out.print("Enter final velocity v1 in m/s: ");
        finalVelocityV1 = userInputScanner.nextDouble();

        System.out.print("Enter time interval t in seconds: ");
        timeIntervalT = userInputScanner.nextDouble();

        // Initialize the cost of driving variable
        double costOfDrivingInDollars = 0.0;

        // Extra nervous check: make sure milesPerGallonValue is not zero before dividing
        if (milesPerGallonValue != 0.0) {
            // Compute how many gallons of fuel will be used for the trip
            double gallonsOfFuelUsed = drivingDistanceInMiles / milesPerGallonValue;

            // Extra nervous check: gallons used should be non-negative
            if (gallonsOfFuelUsed >= 0.0) {
                // Temporary holder variable for the multiplication of gallons and price per gallon
                double temporaryCostHolder = gallonsOfFuelUsed * pricePerGallonInDollars;

                // Assign the temporary cost to the main cost variable
                costOfDrivingInDollars = temporaryCostHolder;
            }
        }

        // Print out the cost of driving with the exact format shown in the example
        System.out.println("The cost of driving is $" + costOfDrivingInDollars);

        // Close the scanner to be safe
        userInputScanner.close();
    }
}