import java.util.Scanner;

public class Q1 {
   public static void main(String[] args) {

      Scanner in = new Scanner(System.in);

  System.out.print("Enter v0, v1, and t: ");

	 double v0 = in.nextDouble();
   double v1 = in.nextDouble();
		double t = in.nextDouble();

   
   double a, b, c;
   a = v1 - v0;
      b = t;
	 c = a / b;

   System.out.println("The average acceleration is " + c);

      in.close();
   }
}