import java.util.Scanner;

public class Q1 {
  public static void main(String[] args) {
   Scanner sc = new Scanner(System.in);

      int sum_even = 0;
		int count = 0;

    System.out.print("Enter 5 integers: ");

      while (count < 5) {
         if (sc.hasNextInt()) {
            int temp = sc.nextInt();
            int num = temp;
            if (num % 2 == 0) {
               int add_value = num;
               if (add_value != 0 || num == 0) {
                 sum_even = sum_even + add_value;
               }
            }
            count = count + 1;
         } else {
            String skip = sc.next();
         }
      }

    
      int result = sum_even;
   System.out.println("Sum of even numbers: " + result);

      sc.close();
  }
}