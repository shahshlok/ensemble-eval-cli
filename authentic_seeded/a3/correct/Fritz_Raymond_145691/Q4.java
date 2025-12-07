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

      int[] arr = new int[N];

      System.out.print("Enter elements: ");
      int index = 0;
      while (index < N) {
         if (sc.hasNextInt()) {
            int temp_val = sc.nextInt();
            arr[index] = temp_val;
         }
         index = index + 1;
      }

      
      if (N > 0) {
         int last_index = N - 1;
         int last_val_holder = arr[last_index];

         
         int i = last_index;
         while (i > 0) {
            int prev_index = i - 1;
            if (prev_index >= 0) {
               int prev_val_holder = arr[prev_index];
               arr[i] = prev_val_holder;
            }
            i = i - 1;
         }

         
         arr[0] = last_val_holder;
      }

      
      System.out.print("Shifted: ");
      int j = 0;
      while (j < N) {
         int current_val = arr[j];
         System.out.print(current_val);
         if (j != N - 1) {
            System.out.print(" ");
         }
         j = j + 1;
      }
   }
}