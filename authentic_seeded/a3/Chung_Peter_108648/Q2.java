import java.util.Scanner;

public class RoadTripCost {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.print("Enter the driving distance in miles: ");
        double distance = input.hasNextDouble() ? input.nextDouble() : 0.0;

        System.out.print("Enter miles per gallon: ");
        double mpg = input.hasNextDouble() ? input.nextDouble() : 1.0;

        System.out.print("Enter price in $ per gallon: ");
        double price = input.hasNextDouble() ? input.nextDouble() : 0.0;

        double cost = (distance / mpg) * price;
        System.out.println("The cost of driving is $" + cost);

        input.close();
    }
}
