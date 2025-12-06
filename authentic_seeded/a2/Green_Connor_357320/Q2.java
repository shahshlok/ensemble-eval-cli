import java.util.Scanner;

public class RoadTripCost
{
   public static void main(String[] ARGS)
   {
      Scanner INPUT = new Scanner(System.in);
      
      System.out.print("Enter the driving distance in miles: ");
      double DISTANCE = INPUT.nextDouble();
      
      System.out.print("Enter miles per gallon: ");
      double MPG = INPUT.nextDouble();
      
      System.out.print("Enter price in $ per gallon: ");
      double PRICE_PER_GALLON = INPUT.nextDouble();
      
      double COST = (DISTANCE / MPG) * PRICE_PER_GALLON;
      
      System.out.println("The cost of driving is $" + COST);
      
      INPUT.close();
   }
}
