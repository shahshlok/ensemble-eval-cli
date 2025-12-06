import java.util.Scanner;

public class RoadTripCostCalculator {

    public static void main(String[] args) {

        Scanner userInputScanner = new Scanner(System.in);


        System.out.print("Enter the driving distance in miles: ");
        double drivingDistanceInMiles = userInputScanner.nextDouble();


        System.out.print("Enter miles per gallon: ");
        double milesPerGallon = userInputScanner.nextDouble();


        System.out.print("Enter price in $ per gallon: ");
        double pricePerGallon = userInputScanner.nextDouble();


        double costOfDriving = (drivingDistanceInMiles / milesPerGallon) * pricePerGallon;


        System.out.println("The cost of driving is $" + costOfDriving);


        userInputScanner.close();
    }
}
