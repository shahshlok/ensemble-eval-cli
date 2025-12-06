import java.util.Scanner;

public class Q1 {
   public static void main(String[] args) {

      Scanner in = new Scanner(System.in);

   double v0 = 0;
   double v1 = 0;
   double t = 0;

   
   double a, b, c;
   a = v1 - v0;
      b = t;
	 c = a / b;

  System.out.print("Enter v0, v1, and t: ");

	 v0 = in.nextDouble();
   v1 = in.nextDouble();
		t = in.nextDouble();

   System.out.println("The average acceleration is " + c);

      in.close();
   }
}