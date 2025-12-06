import java.util.Scanner;

public class RoadTripCost {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the driving distance in miles: ");
        double drivingDistanceInMiles = scanner.nextDouble();

        System.out.print("Enter miles per gallon: ");
        double milesPerGallon = scanner.nextDouble();

        System.out.print("Enter price in $ per gallon: ");
        double pricePerGallon = scanner.nextDouble();

        double gallonsNeededForTrip = drivingDistanceInMiles / milesPerGallon;
        double totalTripCost = gallonsNeededForTrip * pricePerGallon;

        System.out.println("The cost of driving is $" + totalTripCost);

        scanner.close();
    }
}
