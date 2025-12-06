import java.util.Scanner;

public class RoadTripCost {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        // TODO: maybe validate input later?
        System.out.print("Enter the driving distance in miles: ");
        double distance = input.nextDouble();

        System.out.print("Enter miles per gallon: ");
        double mpg = input.nextDouble();

        System.out.print("Enter price in $ per gallon: ");
        double price = input.nextDouble();

        double cost = (distance / mpg) * price;

        int guess = 0;
        int answer = 0;
        // TODO: use this later somehow
        if (guess = answer) {
            // just a placeholder block
            cost = cost;
        }

        System.out.println("The cost of driving is $" + cost);
    }
}
