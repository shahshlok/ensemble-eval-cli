import java.util.Scanner;

public class RoadTripCost {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        double distance, mpg, price, cost;

        System.out.print("Enter the driving distance in miles: ");
        distance = s.nextDouble();
        System.out.print("Enter miles per gallon: ");
        mpg = s.nextDouble();
        System.out.print("Enter price in $ per gallon: ");
        price = s.nextDouble();

        cost = (distance / mpg) * price;

        System.out.println("The cost of driving is $" + cost);
        s.close();
    }
}
