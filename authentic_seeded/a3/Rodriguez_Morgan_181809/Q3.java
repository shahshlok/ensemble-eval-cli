import java.util.Scanner;

public class Q3 {
  public static void main(String[] args) {
   Scanner in = new Scanner(System.in);

	 System.out.print("Enter text: ");
   String input_line = in.nextLine();

   
   int a = 0;
    int b = input_line.length();
	    int c = b - a;

	 String upper_line = input_line.toUpperCase();
  String result_line = upper_line.replace(' ', '_');

    System.out.println("Result: " + result_line);
  }
}