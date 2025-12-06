import java.util.Scanner;

public class RoadTripCost
{
   public static void main(String[] args)
   {
      Scanner SCANNER = new Scanner(System.in);

      double DISTANCE;
      double MPG;
      double PRICE;
      double COST;

      System.out.print("Enter the driving distance in miles: ");
      DISTANCE = SCANNER.nextInt();

      System.out.print("Enter miles per gallon: ");
      MPG = SCANNER.nextInt();

      System.out.print("Enter price in $ per gallon: ");
      PRICE = SCANNER.nextInt();

      COST = (DISTANCE / MPG) * PRICE;

      System.out.println("The cost of driving is $" + COST);
   }
}
