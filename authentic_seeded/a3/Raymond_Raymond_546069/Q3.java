import java.util.Scanner;

public class Q3 {

  public static void main(String[] args) {
  	Scanner input = new Scanner(System.in);

    System.out.print("Enter text: ");
    String line = input.nextLine();

    
      String a = line.toUpperCase();
      String b = a.replace(' ', '_');

   String Result = b;

      System.out.println("Result: " + Result);
  }

}