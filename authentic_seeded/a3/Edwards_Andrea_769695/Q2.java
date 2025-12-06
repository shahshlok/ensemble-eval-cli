import java.util.Scanner;

public class RoadTripCost {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        double totalCost = 0.0;
        for (;;) {
            System.out.printf("Enter the driving distance in miles: ");
            double distance = input.nextDouble();
            System.out.printf("Enter miles per gallon: ");
            double mpg = input.nextDouble();
            System.out.printf("Enter price in $ per gallon: ");
            double price = input.nextDouble();
            double cost = (distance / mpg) * price;
            if (((int) cost) % 2 != 0) {
                break;
            }
            totalCost += cost;
            System.out.printf("The cost of driving is $%s%n", cost);
            if (cost == 0) {
                continue;
            }
        }
        System.out.printf("The cost of driving is $%s%n", totalCost);
    }
}
