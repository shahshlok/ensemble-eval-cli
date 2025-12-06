import java.util.Scanner;

public class RoadTripCost {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        double distance;
        double mpg;
        double price;

        System.out.print("Enter the driving distance in miles: ");
        distance = input.nextDouble();

		System.out.print("Enter miles per gallon: ");
        mpg = input.nextDouble();

        System.out.print("Enter price in $ per gallon: ");
        price = input.nextDouble();

        double cost = (distance / mpg) * price;

        System.out.println("The cost of driving is $" + cost);

        input.close();
    }
}
