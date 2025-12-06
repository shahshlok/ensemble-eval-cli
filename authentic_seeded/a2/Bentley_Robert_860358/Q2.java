import java.util.Scanner;

public class RoadTripCostQ2 {
    public static void main(String[] args) {

        // create scanner object for user input
        Scanner scanner = new Scanner(System.in);

        // initialize all variables to 0 as starting values
        double driving_distance_miles = 0.0;
        double miles_per_gallon = 0.0;
        double price_per_gallon = 0.0;
        double cost_of_driving = 0.0;

        // compute cost BEFORE taking user input (intentional conceptual error)
        cost_of_driving = (driving_distance_miles / miles_per_gallon) * price_per_gallon;

        // prompt user for driving distance in miles
        System.out.print("Enter the driving distance in miles: ");
        driving_distance_miles = scanner.nextDouble();

        // prompt user for miles per gallon (fuel economy)
        System.out.print("Enter miles per gallon: ");
        miles_per_gallon = scanner.nextDouble();

        // prompt user for price in dollars per gallon
        System.out.print("Enter price in $ per gallon: ");
        price_per_gallon = scanner.nextDouble();

        // print the (stale) cost value that was calculated before reading inputs
        System.out.println("The cost of driving is $" + cost_of_driving);

        // close the scanner
        scanner.close();
    }
}
