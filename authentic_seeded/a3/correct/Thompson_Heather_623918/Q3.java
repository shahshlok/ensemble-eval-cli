import java.util.*;

public class Q3 {

   public static void main(String[] args) {
      Scanner sc = new Scanner(System.in);

         System.out.print("Enter text: ");
      String input_line = sc.nextLine();

      
      String a = input_line.toUpperCase();
   	 String b = a.replace(' ', '_');
      String c = b;

      	 System.out.println("Result: " + c);
   }

}