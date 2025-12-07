import java.util.Scanner;

public class Q3 {
  public static void main(String[] args) {
   Scanner input = new Scanner(System.in);

	 System.out.print("Enter text: ");
   String user_text = input.nextLine();

      String upperText = user_text.toUpperCase();
		
	String final_result = upperText.replace(" ", "_");
    
      System.out.println("Result: " + final_result);



  }
}