import java.util.Scanner;

public class Q3 {

  public static void main(String[] args) {
  	Scanner input   = new Scanner(System.in);

    System.out.print("Enter text: ");

    String original = "";
        if (input.hasNextLine()) {
      original = input.nextLine();
        }

    
    String temp_upper = original.toUpperCase();
    
      String result_string = temp_upper.replace(' ', '_');

    if (result_string != null) {
      String holder = result_string;
      	System.out.println("Result: " + holder);
    }

    input.close();
  }

}