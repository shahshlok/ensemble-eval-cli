import java.util.Scanner;

public class Q3 {
  public static void main(String[] args) {

  	Scanner sc = new Scanner(System.in);
    System.out.print("Enter text: ");
    
    String user_input = sc.nextLine();


      String upper = user_input.toUpperCase();
      
  String result_str = upper.replace(' ', '_');

        System.out.println("Result: " + result_str);
  }
}