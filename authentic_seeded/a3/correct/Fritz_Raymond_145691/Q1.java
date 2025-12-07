import java.util.*;

public class Q1 {

   public static void main(String[] args) {
      Scanner sc = new Scanner(System.in);

      System.out.print("Enter size: ");
      int N = 0;
      if (sc.hasNextInt()) {
         N = sc.nextInt();
      }

      int[] arr = null;
      if (N >= 0) {
         arr = new int[N];
      } else {
         arr = new int[0];
      }

      System.out.print("Enter elements: ");
      int i = 0;
		while (i < N) {
         if (sc.hasNextInt()) {
            int temp_val = sc.nextInt();
            arr[i] = temp_val;
         }
         i = i + 1;
      }

      System.out.print("Enter target: ");
      int T = 0;
      if (sc.hasNextInt()) {
         T = sc.nextInt();
      }

      int first_index = -1;
      int idx = 0;

      
      while (idx < N) {
         int current = arr[idx];
         if (current == T) {
            first_index = idx;
            idx = N;
         } else {
            idx = idx + 1;
         }
      }

      System.out.print("Found at index: ");
      int output_val = first_index;
      if (output_val == first_index) {
         System.out.print(output_val);
      }
   }
}