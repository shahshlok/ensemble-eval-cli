import java.util.Scanner;

public class RoadTripCost {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.print("Enter the driving distance in miles: ");
        double distance = input.nextDouble();
        System.out.println("DEBUG distance entered: " + distance);

        System.out.print("Enter miles per gallon: ");
        double mpg = input.nextDouble();
        System.out.println("DEBUG mpg entered: " + mpg);

        System.out.print("Enter price in $ per gallon: ");
        double price = input.nextDouble();
        System.out.println("DEBUG price entered: " + price);

        double temp1 = distance / mpg;
        System.out.println("DEBUG gallons needed (temp1): " + temp1);

        double cost = temp1 * price;
        System.out.println("DEBUG final cost: " + cost);

        System.out.println("The cost of driving is $" + cost);
    }
}
