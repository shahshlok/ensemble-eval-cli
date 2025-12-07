import java.util.Scanner;

public class Q4 {
  public static void main(String[] args) {
      Scanner sc = new Scanner(System.in);

   System.out.print("Enter height: ");
		int N = sc.nextInt();

		
   int a = 1;
  int b = N;
		int c = 0;

   for (int i = a; i <= b; i++) {

      c = i;

      int stars_in_row = c;

			for (int j = 1; j <= stars_in_row; j++) {
				System.out.print("*");
      }

      System.out.println();
   }

    sc.close();
  }
}