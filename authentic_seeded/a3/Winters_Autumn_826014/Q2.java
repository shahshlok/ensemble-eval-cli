import java.util.Scanner;

public class RoadTripCost {
    public static void main(String[] args) {
        Scanner input_scanner = new Scanner(System.in);

        System.out.print("Enter the driving distance in miles: ");
        double distanceMiles = input_scanner.nextDouble();

        System.out.print("Enter miles per gallon: ");
        double miles_per_gallon = input_scanner.nextDouble();

        System.out.print("Enter price in $ per gallon: ");
        double pricePerGallon = input_scanner.nextDouble();

        double cost_of_driving = (distanceMiles / miles_per_gallon) * pricePerGallon;

        System.out.println("The cost of driving is $" + cost_of_driving);

        input_scanner.close();
    }
}
