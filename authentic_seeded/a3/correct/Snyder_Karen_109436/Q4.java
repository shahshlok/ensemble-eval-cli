import java.util.Scanner;

public class Q4 {
   public static void main(String[] args) {
      Scanner sc = new Scanner(System.in);

      System.out.print("Enter size: ");
      int N = 0;
      if (sc.hasNextInt()) {
         N = sc.nextInt();
      }

      if (N < 0) {
         N = 0;
      }

      int[] arr_numbers = new int[N];

      if (N != 0) {
         System.out.print("Enter elements: ");
      }

         int index = 0;
      while (index < N) {
            int temp_input = 0;
            if (sc.hasNextInt()) {
               temp_input = sc.nextInt();
            }
            arr_numbers[index] = temp_input;
         index = index + 1;
      }

      
      if (N > 1) {
          int last_element_holder = arr_numbers[N - 1];

          int i = N - 1;
          while (i > 0) {
                int temp_holder = arr_numbers[i - 1];
             arr_numbers[i] = temp_holder;
             i = i - 1;
          }

          arr_numbers[0] = last_element_holder;
      }

      
      System.out.print("Shifted:");
      int j = 0;
      if (N != 0) {
         System.out.print(" ");
      }
      while (j < N) {
         int value_to_print = arr_numbers[j];
         System.out.print(value_to_print);
         if (j != N - 1) {
            System.out.print(" ");
         }
         j = j + 1;
      }
   }
}