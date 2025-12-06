import java.util.Scanner;

public class RoadTripCost {
    public static void main(String[] args) {
        Scanner input_reader = new Scanner(System.in);

        System.out.print("Enter the driving distance in miles: ");
        double distanceMiles = input_reader.nextDouble();

        System.out.print("Enter miles per gallon: ");
        double miles_per_gallon = input_reader.nextDouble();

        System.out.print("Enter price in $ per gallon: ");
        double pricePerGallon = input_reader.nextDouble();

        double cost = (distanceMiles / miles_per_gallon) * pricePerGallon;

        int guess = 5;
        int answer = 10;
        if (guess = answer) {
            System.out.println("This line will never be reached properly");
        }

        System.out.println("The cost of driving is $" + cost);
    }
}
