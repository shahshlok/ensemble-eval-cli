import java.util.Scanner;

public class RoadTripCost {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        // TODO maybe validate input later
        System.out.print("Enter the driving distance in miles: ");
        double distance = input.nextDouble();

        System.out.print("Enter miles per gallon: ");
        double mpg = input.nextDouble();

        System.out.print("Enter price in $ per gallon: ");
        double pricePerGallon = input.nextDouble();

        // cost = (distance / mpg) * price
        double gallonsUsed = distance / mpg; // maybe round? nah sample doesn't
        double cost = gallonsUsed * pricePerGallon;

        System.out.println("The cost of driving is $" + cost);

        input.close(); // TODO maybe not needed but whatever
    }
}
