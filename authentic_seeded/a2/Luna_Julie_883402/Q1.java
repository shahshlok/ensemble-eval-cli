import java.util.Scanner;

public class Q1 {
   public static void main(String[] args) {
      Scanner input = new Scanner(System.in);

      System.out.print("Enter 5 integers: ");

      int sum_even = 0;

      
      int count = 5;
		int i = 1;

      while (i < count) {

         int a = input.nextInt();
         int b = a % 2;
         int c = a - b;

         if (b == 0) {
            sum_even = sum_even + c;
         }

         i = i + 1;
      }

      System.out.println("Sum of even numbers: " + sum_even);
   }
}