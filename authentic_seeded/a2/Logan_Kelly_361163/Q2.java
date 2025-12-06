import java.util.Scanner;

public class RoadTripCost {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.print("Enter the driving distance in miles: ");
        double distance = input.nextDouble();
        input.close();

        input = new Scanner(System.in);
        System.out.print("Enter miles per gallon: ");
        double mpg = input.nextDouble();
        input.close();

        input = new Scanner(System.in);
        System.out.print("Enter price in $ per gallon: ");
        double pricePerGallon = input.nextDouble();
        input.close();

        double cost = (distance / mpg) * pricePerGallon;
        System.out.println("The cost of driving is $" + cost);
    }
}
