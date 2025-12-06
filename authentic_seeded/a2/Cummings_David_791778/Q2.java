import java.util.Scanner;

public class RoadTripCost
{
   public static void main(String[] args)
   {
      Scanner INPUT = new Scanner(System.in);

      double DISTANCE;
         double MPG;
      double PRICE;
         double COST;

      System.out.print("Enter the driving distance in miles: ");
      DISTANCE = INPUT.nextInt();

      System.out.print("Enter miles per gallon: ");
      MPG = INPUT.nextInt();

      System.out.print("Enter price in $ per gallon: ");
      PRICE = INPUT.nextInt();

      COST = (DISTANCE / MPG) * PRICE;

      System.out.println("The cost of driving is $" + COST);
   }
}
