import java.util.Scanner;

public class RoadTripCost {
    public static void main(String[] args) {
        Scanner inputScanner = new Scanner(System.in);

        System.out.print("Enter the driving distance in miles: ");
        double driving_distance = inputScanner.nextDouble();

        System.out.print("Enter miles per gallon: ");
        double milesPerGallon = inputScanner.nextDouble();

        System.out.print("Enter price in $ per gallon: ");
        double price_per_gallon = inputScanner.nextDouble();

        double costOfDriving = (driving_distance / milesPerGallon) * price_per_gallon;

        System.out.println("The cost of driving is $" + costOfDriving);

        inputScanner.close();
    }
}
