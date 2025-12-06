import java.util.Scanner;

public class RoadTripCost {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        double distance = 0, mpg = 0, price = 0;

        System.out.println("Enter the driving distance in miles: ");
        scanner.nextDouble();

        System.out.println("Enter miles per gallon: ");
        scanner.nextDouble();

        System.out.println("Enter price in $ per gallon: ");
        scanner.nextDouble();

        double cost = (distance / mpg) * price;

        System.out.println("The cost of driving is $" + cost);

        scanner.close();
    }
}
