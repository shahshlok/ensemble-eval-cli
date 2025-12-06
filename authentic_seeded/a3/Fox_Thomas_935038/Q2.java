import java.util.Scanner;

public class RoadTripCost
{
   public static void main(String[] ARGS)
   {
      Scanner INPUT = new Scanner(System.in);

      double DISTANCE;
      double MPG;
      double PRICE;
      double COST;
      int NUMBER;

      System.out.print("Enter the driving distance in miles: ");
      DISTANCE = INPUT.nextDouble();

      System.out.print("Enter miles per gallon: ");
      MPG = INPUT.nextDouble();

      System.out.print("Enter price in $ per gallon: ");
      PRICE = INPUT.nextDouble();

      COST = (DISTANCE / MPG) * PRICE;

      for (NUMBER = 1; NUMBER <= 10; NUMBER++)
      {
         if (NUMBER % 2 != 0)
         {
            continue;
         }
      }

      System.out.println("The cost of driving is $" + COST);
   }
}
