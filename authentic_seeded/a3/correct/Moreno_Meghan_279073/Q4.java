import java.util.*;

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

      System.out.print("Enter elements: ");
      for (int i = 0; i < N; i++) {
         if (sc.hasNextInt()) {
            int temp_val = sc.nextInt();
            arr_numbers[i] = temp_val;
         } else {
            arr_numbers[i] = 0;
         }
      }

      
      if (N > 1) {
          int last_index = N - 1;
          int temp_last = arr_numbers[last_index];

          
          for (int i = last_index; i >= 1; i--) {
             int holder = arr_numbers[i - 1];
             arr_numbers[i] = holder;
          }

          
          if (last_index >= 0) {
             arr_numbers[0] = temp_last;
          }
      }

      
      System.out.print("Shifted:");
      if (N != 0) {
         
         System.out.print(" ");
      }

      
      for (int i = 0; i < N; i++) {
          int value_to_print = arr_numbers[i];
          System.out.print(value_to_print);
          if (i != N - 1) {
             System.out.print(" ");
          }
      }
  }
}