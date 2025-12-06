import java.util.Scanner;

public class RoadTripCostQ2
{
   public static void main(String[] args)
   {
      Scanner INPUT = new Scanner(System.in);

      double DISTANCE = 0;
      double MPG = 0;
      double PRICE = 0;
      double COST = 0;

      System.out.print("Enter the driving distance in miles: ");
      DISTANCE = INPUT.nextDouble();

        System.out.print("Enter miles per gallon: ");
        MPG = INPUT.nextDouble();

      System.out.print("Enter price in $ per gallon: ");

      COST = (DISTANCE / MPG) * PRICE;

      System.out.println("The cost of driving is $" + COST);
   }
}
