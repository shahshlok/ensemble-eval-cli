import java.util.Scanner;

public class RoadTripCostCalculator {

    public static void main(String[] args) {

        Scanner keyboard = new Scanner(System.in);

        System.out.print("Enter the driving distance in miles: ");
        double drivingDistanceInMiles = keyboard.nextDouble();

        System.out.print("Enter miles per gallon: ");
        double milesPerGallon = keyboard.nextDouble();

        System.out.print("Enter price in $ per gallon: ");
        double pricePerGallon = keyboard.nextDouble();


        double costOfDriving = (drivingDistanceInMiles / milesPerGallon) * pricePerGallon;


        System.out.println("The cost of driving is $" + costOfDriving);

        keyboard.close();
    }
}
