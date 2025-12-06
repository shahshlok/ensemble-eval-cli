import java.util.Scanner;

public class Q4TriangleArea
{
   public static void main(String[] args)
   {
      Scanner INPUT = new Scanner(System.in);

      double X1, Y1, X2, Y2, X3, Y3;
      double SIDE1, SIDE2, SIDE3;
      double S, AREA;

      System.out.println("Enter three points for a triangle.");

      System.out.print("(x1, y1):");
      X1 = INPUT.nextDouble();
      Y1 = INPUT.nextDouble();

      System.out.print("(x2, y2):");
      X2 = INPUT.nextDouble();
      Y2 = INPUT.nextDouble();

      System.out.print("(x3, y3):");
      X3 = INPUT.nextDouble();
      Y3 = INPUT.nextDouble();

      SIDE1 = DISTANCE(X1, Y1, X2, Y2);
      SIDE2 = DISTANCE(X2, Y2, X3, Y3);
      SIDE3 = DISTANCE(X3, Y3, X1, Y1);

      S = (SIDE1 + SIDE2 + SIDE3) / 2.0;

      AREA = Math.sqrt(S * (S - SIDE1) * (S - SIDE2) * (S - SIDE3));

      System.out.println("The area of the triangle is " + AREA);
   }

   public static double DISTANCE(double X1, double Y1, double X2, double Y2)
   {
      double DX, DY, D;
      DX = X2 - X1;
      DY = Y2 - Y1;
      D = Math.sqrt(DX * DX + DY * DY);
      return D;
   }
}
