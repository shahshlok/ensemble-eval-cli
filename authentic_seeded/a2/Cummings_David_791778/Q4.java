public class Q4TriangleArea
{
   public static void main(String[] args)
   {
      double X1 = 0;
      double Y1 = 0;
      double X2 = 5;
      double Y2 = 0;
      double X3 = 0;
      double Y3 = 5;

      System.out.println("Enter three points for a triangle.");
      System.out.println("(x1, y1):00");
      System.out.println("(x2, y2):50");
      System.out.println("(x3, y3):05");

      double SIDE1 = Math.sqrt((X2 - X1) * (X2 - X1) + (Y2 - Y1) * (Y2 - Y1));
      double SIDE2 = Math.sqrt((X3 - X2) * (X3 - X2) + (Y3 - Y2) * (Y3 - Y2));
      double SIDE3 = Math.sqrt((X1 - X3) * (X1 - X3) + (Y1 - Y3) * (Y1 - Y3));

      double S = (SIDE1 + SIDE2 + SIDE3) / 2.0;

      double AREA = Math.sqrt(S * (S - SIDE1) * (S - SIDE2) * (S - SIDE3));

      System.out.println("The area of the triangle is " + AREA);
   }
}
