import java.util.Scanner;

public class Q3 {

   public static void main(String[] args) {
      Scanner sc = new Scanner(System.in);

      System.out.print("Enter text: ");
      String   input_line;
      if (sc.hasNextLine()) {
         input_line = sc.nextLine();
      } else {
         input_line = "";
      }

      
      String upper_temp = "";
      if (input_line != null) {
      	upper_temp = input_line.toUpperCase();
      }

      String result_text = upper_temp;
      if (upper_temp != null) {
         result_text = upper_temp.replace(' ', '_');
      }

      if (result_text == null) {
         result_text = "";
      }

      System.out.println("Result: " + result_text);

      sc.close();
   }
}