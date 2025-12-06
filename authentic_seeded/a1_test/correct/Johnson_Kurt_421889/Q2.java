import java.util.Scanner;

public class Q2 {
    public static void main(String[] args) {
        // Create a Scanner object to read user input from the keyboard
        Scanner userInputScanner = new Scanner(System.in);

        // Ask the user for the driving distance in miles
        System.out.print("Enter the driving distance in miles: ");
        double drivingDistanceInMiles = userInputScanner.nextDouble();

        // Ask the user for the fuel economy in miles per gallon
        System.out.print("Enter miles per gallon: ");
        double milesPerGallonFuelEconomy = userInputScanner.nextDouble();

        // Ask the user for the price per gallon of fuel
        System.out.print("Enter price in $ per gallon: ");
        double pricePerGallonInDollars = userInputScanner.nextDouble();

        // Now we will use the math formula:
        // cost = (distance / mpg) * price
        // First declare intermediate math variables a, b, c to structure the formula

        // a will represent the distance divided by miles per gallon (this gives gallons used)
        double a = drivingDistanceInMiles / milesPerGallonFuelEconomy;

        // b will represent the price per gallon in dollars
        double b = pricePerGallonInDollars;

        // c will store the final cost of the driving trip
        double c = a * b;

        // Display the result with the exact format required
        System.out.println("The cost of driving is $" + c);

        // Close the scanner to free system resources
        userInputScanner.close();
    }
}