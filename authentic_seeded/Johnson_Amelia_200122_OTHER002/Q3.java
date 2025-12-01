import java.util.Scanner;

public class Q3 {
   public static void main(String[] args) {
      Scanner reader = new Scanner(System.in);
      double x1 = 1.5;
      double y1 = -3.4;
      double x2 = 4.6;
      double y2 = 5.0;
      double distance = Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
      System.out.println("The distance of the two points is " + distance);
   }
}