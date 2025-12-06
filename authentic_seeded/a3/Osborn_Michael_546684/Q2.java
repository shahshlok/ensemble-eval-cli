import java.util.Scanner;

public class RoadTripCost {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.printf("Enter the driving distance in miles: ");
        double distance = in.nextDouble();
        System.out.printf("Enter miles per gallon: ");
        double mpg = in.nextDouble();
        System.out.printf("Enter price in $ per gallon: ");
        double price = in.nextDouble();
        System.out.printf("The cost of driving is $%s", distance / mpg * price);
        in.close();
    }
}
