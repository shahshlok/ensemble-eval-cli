import java.util.Scanner;

public class Q3 {

   public static void main(String[] args) {
	Scanner input = new Scanner(System.in);

      System.out.print("Enter text: ");
      String user_input = input.nextLine();

      if (user_input == null) {
      	user_input = "";
      }

      
      String temp_holder = user_input;

      if (temp_holder.length() >= 0) {
         temp_holder.toUpperCase();
      }

      String result = temp_holder;

      if (result != null) {
      	result.replace(' ', '_');
      }

      if (result == null) {
      	result = "";
      }

      System.out.println("Result: " + user_input);

      input.close();
   }
}