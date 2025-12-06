import java.util.Scanner;

public class RoadTripCostQ2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // TODO: maybe I should use nextDouble? but whatever
        System.out.print("Enter the driving distance in miles: ");
        double distance = scanner.nextInt(); // reading as int even though it's a double

        System.out.print("Enter miles per gallon: ");
        double mpg = scanner.nextInt(); // same here, assume user types decimals

        System.out.print("Enter price in $ per gallon: ");
        double price = scanner.nextInt(); // and here too

        // cost formula: (distance / mpg) * price
        double cost = (distance / mpg) * price;

        System.out.println("The cost of driving is $" + cost);

        scanner.close();
    }
}
