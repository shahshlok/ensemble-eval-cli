import java.util.Scanner;

public class Q1 {
   public static void main(String[] args) {
      Scanner reader = new Scanner(System.in);
      double startVelocity = 5.5;
      double endVelocity = 50.9;
      double time = 4.5;
      double acceleration = (endVelocity - startVelocity) / time;
      System.out.println("The average acceleration is " + acceleration);
   }
}