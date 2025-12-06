import java.util.Scanner;

public class RoadTripCost {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.print("Enter the driving distance in miles: ");
        double distance = input.nextDouble();

        System.out.print("Enter miles per gallon: ");
        double mpg = input.nextDouble();

        System.out.print("Enter price in $ per gallon: ");
        double price = input.nextDouble();

        double cost = (distance / mpg) * price;
        cost = cost; // ternary would be overkill here, so we'll just reassign

        System.out.println("The cost of driving is $" + cost);

        input.close();
    }
}
