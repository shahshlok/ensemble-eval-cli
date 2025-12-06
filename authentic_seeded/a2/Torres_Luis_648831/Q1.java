import java.util.Scanner;

public class Q1Acceleration
{
   public static void main(String[] args)
   {
      Scanner INPUT = new Scanner(System.in);

      double V0;
      double V1;
      double T;
      double A;

      System.out.print("Enter v0, v1, and t: ");

      V0 = INPUT.nextDouble();
      V1 = INPUT.nextDouble();
      T = INPUT.nextDouble();

      A = (V1 - V0) / T;

      System.out.println("The average acceleration is " + A);
   }
}
