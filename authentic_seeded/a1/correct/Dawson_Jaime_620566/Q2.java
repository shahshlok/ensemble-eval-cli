import java.util.Scanner;

public class Q2 {
    public static void main(String[] args) {
        // Create a Scanner object to read input from the user
        Scanner userInputScanner = new Scanner(System.in);

        // Prompt the user to enter the driving distance in miles
        System.out.print("Enter the driving distance in miles: ");
        double drivingDistanceInMiles = userInputScanner.nextDouble();

        // Prompt the user to enter miles per gallon
        System.out.print("Enter miles per gallon: ");
        double milesPerGallonValue = userInputScanner.nextDouble();

        // Prompt the user to enter price per gallon in dollars
        System.out.print("Enter price in $ per gallon: ");
        double pricePerGallonInDollars = userInputScanner.nextDouble();

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