import java.util.Scanner;

public class Q3 {
  public static void main(String[] args) {
   Scanner input = new Scanner(System.in);

	 System.out.print("Enter text: ");
    String user_line = input.nextLine();

      user_line.toUpperCase();
	user_line.replace(" ", "_");

      System.out.println("Result: " + user_line);
  }
}