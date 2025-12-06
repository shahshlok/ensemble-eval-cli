import java.util.Scanner;

public class RoadTripCost {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        double distance, mpg, price, cost;

        System.out.print("Enter the driving distance in miles: ");
        distance = in.nextDouble();

        System.out.print("Enter miles per gallon: ");
        mpg = in.nextDouble();

        System.out.print("Enter price in $ per gallon: ");
        price = in.nextDouble();

        cost = distance / mpg;
        cost = cost * price;

        System.out.println("The cost of driving is $" + cost);
        in.close();
    }
}
