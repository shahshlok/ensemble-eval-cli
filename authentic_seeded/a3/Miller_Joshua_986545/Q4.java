import java.util.Scanner;

public class Q4_TriangleArea
{
   public static void main(String[] args)
   {
      Scanner INPUT = new Scanner(System.in);

      double X1;
      double Y1;
      double X2;
      double Y2;
      double X3;
      double Y3;

      double SIDE1;
      double SIDE2;
      double SIDE3;

      double S;
      double AREA;

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

      SIDE1 = distance(X1, Y1, X2, Y2);
      SIDE2 = distance(X2, Y2, X3, Y3);
      SIDE3 = distance(X3, Y3, X1, Y1);

      S = (SIDE1 + SIDE2 + SIDE3) / 2.0;

      AREA = Math.sqrt(S * (S - SIDE1) * (S - SIDE2) * (S - SIDE3));

      System.out.println("The area of the triangle is " + AREA);

      // EXTRA PART WITH THE SPECIFIC ERROR
      String STAR;
      System.out.print("*");
      STAR = INPUT.next();
      if (STAR == '*')
      {
         System.out.println("You entered a star.");
      }

      INPUT.close();
   }

   public static double distance(double X1, double Y1, double X2, double Y2)
   {
      double DX;
      double DY;
      double D;

      DX = X2 - X1;
      DY = Y2 - Y1;

      D = Math.sqrt(DX * DX + DY * DY);

      return D;
   }
}
