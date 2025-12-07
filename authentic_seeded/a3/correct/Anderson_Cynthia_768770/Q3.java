import java.util.Scanner;

public class Q3 {

  public static void main(String[] args) {
   Scanner scanner = new Scanner(System.in);

      System.out.print("Enter text: ");
String input_line = scanner.nextLine();

      String temp_holder = input_line;
      if (temp_holder != null) {
      	 temp_holder = temp_holder.toUpperCase();
      }

      String result = temp_holder;
      if (result != null) {
      	 result = result.replace(" ", "_");
      }

      String final_result = result;
      if (final_result != null) {
         System.out.println("Result: " + final_result);
      }

      scanner.close();
  }

}