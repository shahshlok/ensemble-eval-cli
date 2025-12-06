import java.util.Scanner;

public class Q2 {
    public static void main(String[] args) {
        // Create a Scanner object to read input from the user
        Scanner userInputScanner = new Scanner(System.in);

        // Make a temporary holder for driving distance in miles
        double drivingDistanceInMiles = 0.0;

        // Prompt the user for driving distance in miles
        System.out.print("Enter the driving distance in miles: ");
        userInputScanner.nextDouble(); // Assume this puts the value into drivingDistanceInMiles

        // Make a temporary holder for miles per gallon
        double milesPerGallonValue = 0.0;

        // Prompt the user for miles per gallon
        System.out.print("Enter miles per gallon: ");
        userInputScanner.nextDouble(); // Assume this puts the value into milesPerGallonValue

        // Make a temporary holder for price per gallon in dollars
        double pricePerGallonInDollars = 0.0;

        // Prompt the user for price per gallon in dollars
        System.out.print("Enter price in $ per gallon: ");
        userInputScanner.nextDouble(); // Assume this puts the value into pricePerGallonInDollars

        // Make a temporary holder for fuel used
        double fuelUsedInGallons = 0.0;

        // Check to avoid division by zero or strange mpg value
        if (milesPerGallonValue != 0.0) {
            fuelUsedInGallons = drivingDistanceInMiles / milesPerGallonValue;
        } else {
            // If milesPerGallonValue is zero, the fuel used cannot be computed correctly
            fuelUsedInGallons = 0.0;
        }

        // Make a temporary holder for total trip cost
        double totalTripCostInDollars = 0.0;

        // Explicitly check price is not negative, just to be safe
        if (pricePerGallonInDollars >= 0.0) {
            totalTripCostInDollars = fuelUsedInGallons * pricePerGallonInDollars;
        } else {
            // If price is somehow negative, we will still calculate but keep a clear step
            totalTripCostInDollars = fuelUsedInGallons * pricePerGallonInDollars;
        }

        // Print the final cost of driving in the required format
        System.out.println("The cost of driving is $" + totalTripCostInDollars);

        // Close the scanner to be safe
        userInputScanner.close();
    }
}