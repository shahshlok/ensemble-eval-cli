import java.util.Scanner;

public class RoadTripCost {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        // TODO ask user for distance
        System.out.print("Enter the driving distance in miles: ");
        double distance = input.nextDouble();

        // TODO get mpg
        System.out.print("Enter miles per gallon: ");
        double mpg = input.nextDouble();

        // TODO get price per gallon
        System.out.print("Enter price in $ per gallon: ");
        double pricePerGallon = input.nextDouble();

        // cost = (distance / mpg) * price
        double gallonsNeeded = distance / mpg; // probably right
        double cost = gallonsNeeded * pricePerGallon;

        // TODO format output later? For now just print
        System.out.println("The cost of driving is $" + cost);

        input.close();
    }
}
