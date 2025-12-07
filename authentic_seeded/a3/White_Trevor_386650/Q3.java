import java.util.Scanner;

public class Q3 {

   public static void main(String[] args) {
	Scanner sc = new Scanner(System.in);

      System.out.print("Enter text: ");
      String input_line = "";

      if (sc != null) {
         input_line = sc.nextLine();
      }

      String upper_line = "";
      if (input_line != null) {
         upper_line = input_line.toUpperCase();
      }

      String replaced_line = upper_line;
      if (upper_line != null) {
         replaced_line = upper_line.replace(' ', '_');
      }

      
      String resultHolder = replaced_line;
      if (resultHolder != null) {
         System.out.println("Result: " + resultHolder);
      }

      sc.close();
   }
}