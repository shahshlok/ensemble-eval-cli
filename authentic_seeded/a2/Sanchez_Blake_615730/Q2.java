import java.util.Scanner;

public class RoadTripCost {
    public static void main(String[] args) {

        // create a Scanner object for reading user input from the console
        Scanner input_scanner = new Scanner(System.in);

        // declare variables for distance, miles per gallon, price per gallon, and cost
        double driving_distance_miles;
        double miles_per_gallon;
        double price_per_gallon;
        double cost_of_driving;

        // prompt the user for driving distance in miles
        System.out.print("Enter the driving distance in miles: ");
        driving_distance_miles = input_scanner.nextDouble();

        // prompt the user for miles per gallon
        System.out.print("Enter miles per gallon: ");
        miles_per_gallon = input_scanner.nextDouble();

        // prompt the user for price per gallon in dollars
        System.out.print("Enter price in $ per gallon: ");
        price_per_gallon = input_scanner.nextDouble();

        // calculate the cost of driving using the formula:
        // (distance / mpg) * price
        cost_of_driving = (driving_distance_miles / miles_per_gallon) * price_per_gallon;

        // display the result to the user
        System.out.println("The cost of driving is $" + cost_of_driving);

        // close the scanner to avoid resource leak
        input_scanner.close();
    }
}
