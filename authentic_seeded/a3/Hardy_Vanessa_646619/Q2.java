import java.util.Scanner;

public class RoadTripCost {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        double d, mpg, p;
        System.out.print("Enter the driving distance in miles: ");
        d = s.nextDouble();
        System.out.print("Enter miles per gallon: ");
        mpg = s.nextDouble();
        System.out.print("Enter price in $ per gallon: ");
        p = s.nextDouble();
        d = (d / mpg) * p;
        System.out.println("The cost of driving is $" + d);
        s.close();
    }
}
