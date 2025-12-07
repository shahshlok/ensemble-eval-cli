import java.util.Scanner;

public class Q3 {
   public static void main(String[] args) {
      Scanner input_scanner = new Scanner(System.in);

      System.out.print("Enter text: ");
      
      String userInput = "";
      if (input_scanner.hasNextLine()) {
      	  userInput = input_scanner.nextLine();
      }

      
      String upperVersion = userInput;
      if (userInput != null) {
          userInput.toUpperCase();
      }

      String resultString = userInput;
      if (userInput != null) {
      	  userInput.replace(' ', '_');
      }

      
      String holder = resultString;
      if (holder == null) {
          holder = "";
      }

      System.out.println("Result: " + userInput);

      input_scanner.close();
   }
}