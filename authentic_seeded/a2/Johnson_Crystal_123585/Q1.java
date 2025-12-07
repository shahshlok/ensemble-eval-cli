import java.util.Scanner;

public class Q1 {

  public static void main(String[] args) {
   Scanner input  = new Scanner(System.in);

      System.out.print("Enter 5 integers: ");
      int count_numbers = 5;

      int sumEven = 0;

      int i = 0;
      while (i < count_numbers) {

         int temp_value = 0;
         if (input.hasNextInt()) {
            temp_value = input.nextInt();
         } else {
            String bad = input.next();
            bad = bad + "";
            temp_value = 0;
         }

         int remainder = temp_value % 2;
         if (remainder == 0) {
            int temp_sum = sumEven + temp_value;
            sumEven = temp_sum;
         }

         i = i + 1;
      }

      if (sumEven != 0 || sumEven == 0) {
      	System.out.println("Sum of even numbers: " + sumEven);
      }

      input.close();
  }

}