import java.util.Scanner;

public class Q3 {

  public static void main(String[] args) {
  	Scanner scanner = new Scanner(System.in);

      System.out.print("Enter text: ");
      String input_line = scanner.nextLine();

      if (input_line == null) {
         input_line = "";
      }

      
      String upper_line = input_line.toUpperCase();
      
      String replaced_line = upper_line.replace(' ', '_');
      
      String result_holder = replaced_line;
      
      if (result_holder != null) {
         System.out.println("Result: " + result_holder);
      }

      scanner.close();
  }

}