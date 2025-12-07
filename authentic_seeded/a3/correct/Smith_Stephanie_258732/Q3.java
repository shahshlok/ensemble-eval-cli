import java.util.Scanner;

public class Q3 {
  public static void main(String[] args) {
      Scanner input_scanner = new Scanner(System.in);

      System.out.print("Enter text: ");
      
      String userInput = "";
      if (input_scanner.hasNextLine()) {
         userInput = input_scanner.nextLine();
      }

      
      String upperVersion = "";
      if (userInput != null) {
         upperVersion = userInput.toUpperCase();
      }

      String resultString = upperVersion;
      if (upperVersion != null) {
      	 resultString = upperVersion.replace(' ', '_');
      }

      
      String holder = resultString;
      if (holder == null) {
         holder = "";
      }

      System.out.println("Result: " + holder);

      input_scanner.close();
  }
}