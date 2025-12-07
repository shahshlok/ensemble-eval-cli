import java.util.Scanner;

public class Q3 {

  public static void main(String[] args) {
  	Scanner input   = new Scanner(System.in);

    System.out.print("Enter text: ");

    String original = "";
        if (input.hasNextLine()) {
      original = input.nextLine();
        }

    
    original.toUpperCase();
    
      original.replace(' ', '_');

    if (original != null) {
      String holder = original;
      	System.out.println("Result: " + holder);
    }

    input.close();
  }

}