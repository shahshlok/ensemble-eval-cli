import java.util.Scanner;

public class Main
{

  public static void main(String[] args)
  {
    double V0 = 0;
    double V1 = 0;
    double T = 0;

    double A = (V1 - V0) / T;

      Scanner INPUT = new Scanner(System.in);

      System.out.print("Enter v0, v1, and t: ");

      V0 = INPUT.nextDouble();
      V1 = INPUT.nextDouble();
      T = INPUT.nextDouble();

      System.out.println("The average acceleration is " + A);
  }
}
