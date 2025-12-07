import java.util.Scanner;

public class Q3 {
  public static void main(String[] args) {
   Scanner input   = new Scanner(System.in);

	 System.out.print("Enter text: ");
    String user_input = "";

    if (input != null) {
      user_input = input.nextLine();
    }

    String tempHolder = user_input;

    if (tempHolder != null) {
      tempHolder = tempHolder.toUpperCase();
    }

    String result_string = tempHolder;

    
    if (result_string != null && result_string.length() >= 0) {
       result_string = result_string.replace(' ', '_');
    }

	 String final_output = result_string;

    if (final_output != null) {
      System.out.println("Result: " + final_output);
    }

    input.close();
  }
}