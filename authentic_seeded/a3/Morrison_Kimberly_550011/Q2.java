import java.util.Scanner;

public class RoadTripCost {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        double cost = 0.0;

        while (true) {
            System.out.print("Enter the driving distance in miles: ");
            double distance = input.nextDouble();

            System.out.print("Enter miles per gallon: ");
            double mpg = input.nextDouble();

            System.out.print("Enter price in $ per gallon: ");
            double price = input.nextDouble();

            cost = (distance > 0 && mpg > 0 && price > 0)
                    ? (distance / mpg) * price
                    : 0.0;

            System.out.println("The cost of driving is $" + cost);

            double fancyCheck = distance + mpg + price;
            if (((int) fancyCheck) % 2 != 0) {
                continue;
            } else {
                continue;
            }
        }
    }
}
