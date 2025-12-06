import java.util.Scanner;

public class RoadTripCost {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        double distance = readDouble(input, "Enter the driving distance in miles: ");
        double mpg = readDouble(input, "Enter miles per gallon: ");
        double pricePerGallon = readDouble(input, "Enter price in $ per gallon: ");

        double cost = calculateCost(distance, mpg, pricePerGallon);

        System.out.println("The cost of driving is $" + cost);
    }

    public static double readDouble(Scanner input, String message) {
        System.out.print(message);
        double value = input.nextDouble();
        return value;
    }

    public static double calculateCost(double distance, double mpg, double pricePerGallon) {
        double gallonsNeeded = distance / mpg;
        double totalCost = gallonsNeeded * pricePerGallon;
        return totalCost;
    }
}
