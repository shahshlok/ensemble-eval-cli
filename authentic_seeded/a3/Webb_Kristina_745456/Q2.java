import java.util.Scanner;

public class RoadTripCost {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        // TODO maybe validate inputs later
        System.out.print("Enter the driving distance in miles: ");
        double distance = input.nextDouble();

        System.out.print("Enter miles per gallon: ");
        double mpg = input.nextDouble();

        System.out.print("Enter price in $ per gallon: ");
        double price = input.nextDouble();

        double cost = (distance / mpg) * price;

        // TODO maybe format the cost to 2 decimal places?
        System.out.println("The cost of driving is $" + cost);

        int guess = 5;
        int answer = 5;
        if (guess = answer) {
            System.out.println("Just testing equality stuff"); // half-finished idea
        }
    }
}
