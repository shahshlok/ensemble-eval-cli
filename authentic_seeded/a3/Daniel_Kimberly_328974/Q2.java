import java.util.Scanner;

public class RoadTripCostCalculator {

    public static void main(String[] args) {

        // create scanner object to read user input from the console
        Scanner input_scanner = new Scanner(System.in);

        // declare variable to hold the driving distance in miles
        double driving_distance_miles = 0.0;

        // declare variable to hold the fuel efficiency in miles per gallon
        double miles_per_gallon = 0.0;

        // declare variable to hold the price of fuel in dollars per gallon
        double price_per_gallon = 0.0;

        // declare variable to hold the final cost of driving
        double cost_of_driving = 0.0;

        // ask the user to enter the driving distance in miles
        System.out.print("Enter the driving distance in miles: ");

        // read the driving distance from the user
        driving_distance_miles = input_scanner.nextDouble();

        // ask the user to enter the miles per gallon value
        System.out.print("Enter miles per gallon: ");

        // read the miles per gallon from the user
        miles_per_gallon = input_scanner.nextDouble();

        // ask the user to enter the price in dollars per gallon
        System.out.print("Enter price in $ per gallon: ");

        // read the price per gallon from the user
        price_per_gallon = input_scanner.nextDouble();

        // compute the cost of driving using the formula:
        // cost = (distance / mpg) * price
        cost_of_driving = (driving_distance_miles / miles_per_gallon) * price_per_gallon;

        // display the cost of driving to the user
        System.out.println("The cost of driving is $" + cost_of_driving);

        // ---------------------------------------------------------------------------------
        // below is an unrelated guessing loop included to practice while-loops and input
        // it intentionally contains a logical bug as per instructions
        // ---------------------------------------------------------------------------------

        // declare the correct answer for the guessing game
        int correct_answer = 7;

        // prompt the user for an initial guess
        System.out.print("Guess the secret number between 1 and 10: ");

        // read the initial guess from the user ONE TIME before the loop
        int user_guess = input_scanner.nextInt();

        // while the guess is not equal to the correct answer, keep looping
        while (user_guess != correct_answer) {

            // if the guess is too low, give a hint
            if (user_guess < correct_answer) {
                System.out.println("Too low, try again!");
            }

            // if the guess is too high, give a hint
            if (user_guess > correct_answer) {
                System.out.println("Too high, try again!");
            }

            // the loop now goes back to the top and checks the same guess again
        }

        // if the loop finishes, then the user must have guessed correctly
        System.out.println("You got it!");

        // close the scanner resource
        input_scanner.close();
    }
}
