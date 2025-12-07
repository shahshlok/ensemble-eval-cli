import java.util.*;

public class Q3 {

   public static void main(String[] args) {
      Scanner sc = new Scanner(System.in);

         System.out.print("Enter text: ");
      String input_line = sc.nextLine();

      
      String a = input_line;
   	 String b = a;
      String c = b;

      a.toUpperCase();
      b.replace(' ', '_');

      	 System.out.println("Result: " + c);
   }

}