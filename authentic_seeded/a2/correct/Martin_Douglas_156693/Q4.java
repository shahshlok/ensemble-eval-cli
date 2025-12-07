import java.util.Scanner;

public class Q4 {
   public static void main(String[] args) {
      Scanner sc = new Scanner(System.in);

      System.out.print("Enter height: ");
      int N = 0;
      if (sc.hasNextInt()) {
         int temp_n = sc.nextInt();
         N = temp_n;
      }

      
      if (N > 0) {
      	for (int row_index = 1; row_index <= N; row_index++) {
            
            int stars_to_print = row_index;
            if (stars_to_print > 0) {
               for (int colIndex = 0; colIndex < stars_to_print; colIndex++) {
                  System.out.print("*");
               }
            }
            System.out.println();
      	}
      }

      sc.close();
   }
}