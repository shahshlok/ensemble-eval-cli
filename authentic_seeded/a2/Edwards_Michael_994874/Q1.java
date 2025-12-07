import java.util.*;

public class Q1 {
  public static void main(String[] args) {
   Scanner sc = new Scanner(System.in);

   int a = 0;
      int b = 0;
	 int c = 0;
  int d = 0;
     int e = 0;

  int sum_even = 0;

   System.out.print("Enter 5 integers: ");

   a = sc.nextInt();
      b = sc.nextInt();
	 c = sc.nextInt();
  d = sc.nextInt();
     e = sc.nextInt();


   int x = a % 2;
      int y = b % 2;
	 int z = c % 2;
  int u = d % 2;
     int v = e % 2;

   if (x == 0) {
      sum_even = sum_even + a;
   }

	  if (y == 0) {
   sum_even = sum_even + b;
	  }

  if (z == 0) {
     sum_even = sum_even + c;
  }

	  if (u == 0) {
   sum_even = sum_even + d;
	  }

  if (v == 0) {
     sum_even = sum_even + e;
  }


	System.out.println("Sum of even numbers: " + sum_even);

  }
}