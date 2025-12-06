import java.util.Scanner;

public class DistanceBetweenPoints
{
   public static void main(String[] args)
   {
      Scanner SCANNER = new Scanner(System.in);

      System.out.print("Enter x1 and y1: ");
      double X1 = SCANNER.nextDouble();
      double Y1 = 0, X2 = 0, Y2 = 0;

      System.out.print("Enter x2 and y2: ");

      double DX = X2 - X1;
      double DY = Y2 - Y1;
      double DISTANCE = Math.sqrt(DX * DX + DY * DY);

      System.out.println("The distance of the two points is " + DISTANCE);
   }
}
