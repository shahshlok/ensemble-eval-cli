import java.util.Scanner;

public class Q1 {
   public static void main(String[] args) {
      Scanner reader = new Scanner(System.in);
      System.out.print("Enter v0, v1, and t: ");
      // double vStart=reader.nextDouble();
      // double vEnd=reader.nextDouble();
      // double t=reader.nextDouble();
      double vStart = 5.5;
      double vEnd = 10.5;
      double t = 4.5;
      double a = (vEnd - vStart) / t;
      System.out.println("The average acceleration is " + a);
   }
}