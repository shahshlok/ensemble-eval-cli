import java.util.Scanner;

public class RoadTripCost
{
   public static void main(String[] args)
   {
      Scanner INPUT = new Scanner(System.in);

      double DISTANCE;
      double MPG;
      double PRICE_PER_GALLON;
      double COST;

      System.out.print("Enter the driving distance in miles: ");
      DISTANCE = INPUT.nextDouble();

      System.out.print("Enter miles per gallon: ");
      MPG = INPUT.nextDouble();

      System.out.print("Enter price in $ per gallon: ");
      PRICE_PER_GALLON = INPUT.nextDouble();

      COST = (DISTANCE / MPG) * PRICE_PER_GALLON;

      System.out.println("The cost of driving is $" + COST);

      INPUT.close();
   }
}
