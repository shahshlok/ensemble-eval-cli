import java.util.Scanner;

public class Q3 {

   public static void main(String[] args) {
      Scanner sc = new Scanner(System.in);

        System.out.print("Enter text: ");
      String input_line = "";

      if (sc != null) {
      	input_line = sc.nextLine();
      }

      
      String upper = "";
      if (input_line != null) {
         upper = input_line.toUpperCase();
      }

         String result = "";
      if (upper != null) {
      	result = upper.replace(' ', '_');
      }


      String output_holder = result;
      if (output_holder != null) {
         System.out.println("Result: " + output_holder);
      }

      sc.close();
   }
}