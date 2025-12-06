import java.util.Scanner;

public class Acceleration
{
   public static void main(String[] args)
   {
      Scanner INPUT = new Scanner(System.in);
      
      double V0;
      double V1;
      double T;
      double AVERAGE_ACCELERATION;

      System.out.print("Enter v0, v1, and t: ");
      V0 = INPUT.nextDouble();
      V1 = INPUT.nextDouble();
      T = INPUT.nextDouble();

      AVERAGE_ACCELERATION = (V1 - V0) / T;

      System.out.println("The average acceleration is " + AVERAGE_ACCELERATION);
   }
}
