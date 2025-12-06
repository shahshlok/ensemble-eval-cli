import java.util.Scanner;

public class DistanceBetweenPoints
{

   public static void main(String[] args)
   {
      Scanner INPUT = new Scanner(System.in);

      System.out.print("Enter x1 and y1: ");
      double X1 = INPUT.nextDouble();
      double Y1 = INPUT.nextDouble();

      System.out.print("Enter x2 and y2: ");
      double X2 = INPUT.nextDouble();
      double Y2 = INPUT.nextDouble();

      double DX = X2 - X1;
      double DY = Y2 - Y1;

      double DISTANCE = Math.sqrt(DX * DX + DY * DY);

      System.out.println("The distance of the two points is " + DISTANCE);

      INPUT.close();
   }

}
