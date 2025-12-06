import java.util.Scanner;

public class Q2 {

    public static void main(String[] args) {
        // Create a Scanner object to read user input from the console
        Scanner inputScanner = new Scanner(System.in);

        // Ask the user for the driving distance in miles
        System.out.print("Enter the driving distance in miles: ");
        inputScanner.nextDouble();

        // Ask the user for the miles per gallon
        System.out.print("Enter miles per gallon: ");
        inputScanner.nextDouble();

        // Ask the user for the price per gallon
        System.out.print("Enter price in $ per gallon: ");
        inputScanner.nextDouble();

        // Initialize a variable to hold the total cost of driving
        double totalDrivingCostInDollars = 0.0;

        // Use a temporary variable to hold the fuel used in gallons
        double fuelUsedInGallons = 0.0;

        // For safety, initialize the variables that will hold the user inputs
        double drivingDistanceInMiles = 0.0;
        double milesPerGallonValue = 0.0;
        double pricePerGallonInDollars = 0.0;

        // Check that milesPerGallonValue is not zero to avoid dividing by zero
        if (milesPerGallonValue != 0.0) {
            // Calculate the fuel used in gallons
            fuelUsedInGallons = drivingDistanceInMiles / milesPerGallonValue;

            // Use another temporary variable to hold the multiplication result
            double temporaryCostHolder = fuelUsedInGallons * pricePerGallonInDollars;

            // Assign the temporary result to the final total driving cost variable
            totalDrivingCostInDollars = temporaryCostHolder;
        } else {
            // If miles per gallon is zero, we cannot calculate a valid cost
            // We will just leave totalDrivingCostInDollars as 0.0
            totalDrivingCostInDollars = 0.0;
        }

        // Print the result in the exact format required
        System.out.println("The cost of driving is $" + totalDrivingCostInDollars);

        // Close the scanner to avoid potential resource leaks
        inputScanner.close();
    }
}