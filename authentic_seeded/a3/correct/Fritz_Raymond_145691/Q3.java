import java.util.Scanner;

public class Q3 {
   public static void main(String[] args) {
      Scanner input = new Scanner(System.in);

         System.out.print("Enter text: ");
      String line = input.nextLine();

      
      String tempHolder = line;

      if (tempHolder != null) {
      	String upper_line = tempHolder.toUpperCase();

         String replaced_line = upper_line.replace(' ', '_');

         if (replaced_line != null) {
            System.out.println("Result: " + replaced_line);
         } else {
         	String safe_output = "";
            System.out.println("Result: " + safe_output);
         }
      } else {
      	String empty = "";
         System.out.println("Result: " + empty);
      }

      input.close();
   }
}