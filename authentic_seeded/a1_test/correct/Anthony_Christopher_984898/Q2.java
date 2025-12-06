import java.util.Scanner;

public class Q2 {
    public static void main(String[] args) {
        // Step 1: Create a Scanner so we can read input from the user
        Scanner keyboardScanner = new Scanner(System.in);

        // Step 2: Ask the user for the driving distance in miles
        System.out.print("Enter the driving distance in miles: ");
        double drivingDistanceInMiles = keyboardScanner.nextDouble();

        // Step 3: Ask the user for the miles per gallon
        System.out.print("Enter miles per gallon: ");
        double milesPerGallonValue = keyboardScanner.nextDouble();

        // Step 4: Ask the user for the price per gallon in dollars
        System.out.print("Enter price in $ per gallon: ");
        double pricePerGallonInDollars = keyboardScanner.nextDouble();

        // Step 5: Calculate how many gallons of fuel we will use
        double gallonsOfFuelUsed = drivingDistanceInMiles / milesPerGallonValue;

        // Step 6: Calculate the total cost of the road trip
        double totalCostOfDriving = gallonsOfFuelUsed * pricePerGallonInDollars;

        // Step 7: Print the final cost of driving
        System.out.println("The cost of driving is $" + totalCostOfDriving);

        // Step 8: Close the scanner because we are done using it
        keyboardScanner.close();
    }
}