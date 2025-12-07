import java.util.Scanner;

public class Q1 {
   public static void main(String[] args) {
      Scanner input = new Scanner(System.in);

      System.out.print("Enter 5 integers: ");

      int sum_even = 0;

      int a = input.nextInt();
      int b = a % 2;
      int c = a - b;
      if (b == 0) {
         sum_even = sum_even + c;
      }

		int d = input.nextInt();
      int e = d % 2;
      int f = d - e;
		if (e == 0) {
   sum_even = sum_even + f;
		}

      int g = input.nextInt();
      int h = g % 2;
      int i = g - h;

      if (h == 0) {
        sum_even = sum_even + i;
      }

		int j = input.nextInt();
		int k = j % 2;
		int l = j - k;

		if (k == 0) {
			sum_even = sum_even + l;
		}

   int m = input.nextInt();
      int n = m % 2;
   int o = m - n;

      if (n == 0) {
      sum_even = sum_even + o;
      }

      System.out.println("Sum of even numbers: " + sum_even);
   }
}