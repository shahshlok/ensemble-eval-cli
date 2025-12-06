import java.util.Scanner;

public class Q1 {
   public static void main(String[] args)   {
      Scanner input = new Scanner(System.in);

      System.out.print("Enter v0, v1, and t: ");

      double v0 = 0.0;
         double v1 = 0.0;
      double t  = 0.0;

      if (input != null) {
          v0 = input.nextDouble();
      }
      
      	if (input != null) {
      v1 = input.nextDouble();
        }

      if (input != null) {
         t = input.nextDouble();
      }

      
      double delta_v = v1 - v0;
      double acceleration = 0.0;

      if (t != 0.0) {
          acceleration = (double)(v1 - v0 / t);
      } else {
          acceleration = 0.0;
      }

      double result_holder = acceleration;
      
      System.out.println("The average acceleration is " + result_holder);

      input.close();
   }
}