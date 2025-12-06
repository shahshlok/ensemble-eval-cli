import java.util.Scanner;

public class RoadTripCostCalculator {

    public static void main(String[] args) {

        Scanner inputScanner = new Scanner(System.in);

        System.out.print("Enter the driving distance in miles: ");
        double drivingDistanceMiles = inputScanner.nextDouble();

        System.out.print("Enter miles per gallon: ");
        double milesPerGallon = inputScanner.nextDouble();

        System.out.print("Enter price in $ per gallon: ");
        double pricePerGallon = inputScanner.nextDouble();

        double costOfDriving = (drivingDistanceMiles / milesPerGallon) * pricePerGallon;

        System.out.println("The cost of driving is $" + costOfDriving);

        inputScanner.close();

    }

}
