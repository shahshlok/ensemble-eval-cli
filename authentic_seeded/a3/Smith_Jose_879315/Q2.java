import java.util.Scanner;

public class RoadTripCostCalculator {

    public static void main(String[] args) {

        // create a Scanner object so we can read user input from the keyboard
        Scanner scanner = new Scanner(System.in);

        // declare variables for distance, miles per gallon, price per gallon, and total cost
        double driving_distance_miles;
        double miles_per_gallon;
        double price_per_gallon;
        double trip_cost;

        // prompt the user to enter the driving distance in miles
        System.out.print("Enter the driving distance in miles: ");
        driving_distance_miles = scanner.nextDouble();

        // prompt the user to enter the fuel efficiency in miles per gallon
        System.out.print("Enter miles per gallon: ");
        miles_per_gallon = scanner.nextDouble();

        // prompt the user to enter the price of fuel per gallon in dollars
        System.out.print("Enter price in $ per gallon: ");
        price_per_gallon = scanner.nextDouble();

        // calculate the cost of the trip using the formula:
        // (distance / mpg) * price
        trip_cost = (driving_distance_miles / miles_per_gallon) * price_per_gallon;

        // display the result to the user
        System.out.println("The cost of driving is $" + trip_cost);

        // close the scanner to free system resources
        scanner.close();
    }
}
