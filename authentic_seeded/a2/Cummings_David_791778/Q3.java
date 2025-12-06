import java.util.Scanner;

public class Q3
{

   public static void main(String[] ARGS)
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

      double DX2 = Math.pow(2, DX);
      double DY2 = Math.pow(2, DY);

      double SUM = DX2 + DY2;

      double DISTANCE = Math.sqrt(SUM);

      System.out.println("The distance of the two points is " + DISTANCE);
   }

}
