import java.util.*;

public class Q4 {
  public static void main(String[] args) {
      Scanner sc = new Scanner(System.in);

      System.out.print("Enter size: ");
      int N = 0;
      if (sc.hasNextInt()) {
         N = sc.nextInt();
      }

      
      int[] arr = new int[N];
      if (N > 0) {
         System.out.print("Enter elements: ");
      }

      int idx = 0;
      while (idx < N) {
          if (sc.hasNextInt()) {
             int temp_val = sc.nextInt();
             arr[idx] = temp_val;
          }
          idx = idx + 1;
      }

      
      if (N > 0) {
      	 int last_index = N - 1;
      	 int last_val = arr[last_index];

         
         int i = last_index;
         while (i > 0) {
         	   int holder = arr[i - 1];
         	   arr[i] = holder;
         	   i = i - 1;
         }

         
         arr[0] = last_val;
      }

      
      System.out.print("Shifted:");
      int j = 0;
      while (j < N) {
      	int val_to_print = arr[j];
      	System.out.print(" " + val_to_print);
      	j = j + 1;
      }
  }
}