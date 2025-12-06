import java.util.Scanner;

public class RoadTripCostCalculator {

    public static void main(String[] args) {

        // create a Scanner object to read input from the user
        Scanner input = new Scanner(System.in);

        // declare variables to store user inputs
        double driving_distance_miles;
        double miles_per_gallon;
        double price_per_gallon;
        double total_cost;

        // prompt user for driving distance in miles
        System.out.print("Enter the driving distance in miles: ");
        driving_distance_miles = input.nextDouble();

        // prompt user for miles per gallon (fuel economy)
        System.out.print("Enter miles per gallon: ");
        miles_per_gallon = input.nextDouble();

        // prompt user for price per gallon of fuel in dollars
        System.out.print("Enter price in $ per gallon: ");
        price_per_gallon = input.nextDouble();

        // calculate the total cost of the trip using the formula:
        // (distance / mpg) * price_per_gallon
        total_cost = (driving_distance_miles / miles_per_gallon) * price_per_gallon;

        // display the result to the user
        System.out.println("The cost of driving is $" + total_cost);

        // close the scanner
        input.close();
    }
}
