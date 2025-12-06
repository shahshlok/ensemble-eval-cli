import java.util.Scanner;

public class TriangleAreaQ4
{
   public static void main(String[] args)
   {
      Scanner INPUT = new Scanner(System.in);

      System.out.println("Enter three points for a triangle.");

      System.out.print("(x1, y1):");
      double X1 = INPUT.nextDouble();
      double Y1 = INPUT.nextDouble();

      System.out.print("(x2, y2):");
      double X2 = INPUT.nextDouble();
      double Y2 = INPUT.nextDouble();

      System.out.print("(x3, y3):");
      double X3 = INPUT.nextDouble();
      double Y3 = INPUT.nextDouble();

      double SIDE1 = distance(X1, Y1, X2, Y2);
      double SIDE2 = distance(X2, Y2, X3, Y3);
      double SIDE3 = distance(X3, Y3, X1, Y1);

      double S = (SIDE1 + SIDE2 + SIDE3) / 2.0;

      double AREA = Math.sqrt(S * (S - SIDE1) * (S - SIDE2) * (S - SIDE3));

      System.out.println("The area of the triangle is " + AREA);
   }

   public static double distance(double X1, double Y1, double X2, double Y2)
   {
      double DX = X2 - X1;
      double DY = Y2 - Y1;
      double DIST = Math.sqrt(DX * DX + DY * DY);
      return DIST;
   }
}
