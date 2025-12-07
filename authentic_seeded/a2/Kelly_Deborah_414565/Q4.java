import java.util.Scanner;

public class Q4 {
  public static void main(String[] args) {
   Scanner input = new Scanner(System.in);

	 System.out.print("Enter height: ");
    int N = input.nextInt();


 for (int row_number = 1; row_number <= N; row_number++) {

	  for (int col = 1; col <= row_number; row_number++) {
      System.out.print("*");
	  }

   System.out.println();
 }
  }
}