import java.util.Scanner;

public class Q3 {

  public static void main(String[] args) {
   Scanner input = new Scanner(System.in);

      System.out.print("Enter text: ");
      String original_line = input.nextLine();

      String safe_line = original_line;
      
      if (safe_line == null) {
       safe_line = "";
      }

      

      String upper_line = safe_line.toUpperCase();
      
      	 String replaced_line = upper_line.replace(' ', '_');

      String result = replaced_line;

      if (result != null) {
       System.out.println("Result: " + result);
      } else {
       System.out.println("Result: ");
      }

      input.close();
  }
}