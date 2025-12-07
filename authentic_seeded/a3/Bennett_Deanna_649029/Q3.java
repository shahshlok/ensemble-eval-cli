import java.util.Scanner;

public class Q3 {
  public static void main(String[] args) {

  	Scanner sc = new Scanner(System.in);
    System.out.print("Enter text: ");
    
    String user_input = sc.nextLine();


      user_input.toUpperCase();
      
  user_input.replace(' ', '_');

        System.out.println("Result: " + user_input);
  }
}