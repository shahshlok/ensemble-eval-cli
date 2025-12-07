import java.util.Scanner;

public class Q4 {
   public static void main(String[] args) {
      Scanner input = new Scanner(System.in);

      System.out.print("Enter height: ");
      int N = input.nextInt();



      int a = 1;
      int b = N;
      int total_rows = b - a + 1;

		for (int row = 1; row <= total_rows; row++) {
      int x = row;
      int y = 0;
      int stars_in_row = x + y;

         for (int col = 1; col <= stars_in_row; col++) {
				System.out.print("*");
         }
         
         System.out.println();
		}
   }
}