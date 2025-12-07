import java.util.Scanner;

public class Q3 {

  public static void main(String[] args) {
  	Scanner scanner = new Scanner(System.in);

    System.out.print("Enter text: ");
    String input_line = scanner.nextLine();



    if (input_line == null) {
       input_line = "";
    }

    String upper_temp = input_line.toUpperCase();
    String result = upper_temp.replace(' ', '_');

    if (result != null) {
        System.out.println("Result: " + result);
    }

    scanner.close();
  }
}