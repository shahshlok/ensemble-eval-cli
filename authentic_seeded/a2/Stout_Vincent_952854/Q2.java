import java.util.Scanner;

public class RoadTripCost {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.print("Enter the driving distance in miles: ");
        double distance = input.nextDouble();

        System.out.print("Enter miles per gallon: ");
        double mpg = input.nextDouble();

        System.out.print("Enter price in $ per gallon: ");
        double pricePerGallon = input.nextDouble();

        double cost = (distance / mpg) * pricePerGallon;

        System.out.print("The cost of driving is $");
        System.out.printf("%.2f", cost);
        System.out.println();

        // Conceptual error: assuming cost is now rounded because of printf
        double costWithTax = cost * 1.15;
        System.out.println("The cost of driving with 15% tax is $" + costWithTax);

        input.close();
    }
}
