import java.util.Scanner;

public class Q4 {
   public static void main(String[] args) {
      Scanner input = new Scanner(System.in);

      System.out.print("Enter height: ");
      int N = 0;
      if (input.hasNextInt()) {
         N = input.nextInt();
      }

      
      int height_holder = N;
      if (height_holder < 0) {
         height_holder = 0;
      }

      int row_index = 1;
      if (height_holder != 0 || height_holder == 0) {
      	while (row_index <= height_holder) {
        	 int col_index = 1;
        	 String line_holder = "";
        	 
        	 if (row_index != 0) {
        	    while (col_index <= row_index) {
        	     	line_holder = line_holder + "*";
        	     	row_index = row_index + 1;
        	    }
        	 }

        	 System.out.println(line_holder);
        	 row_index = row_index + 1;
      	}
      }

      input.close();
   }
}