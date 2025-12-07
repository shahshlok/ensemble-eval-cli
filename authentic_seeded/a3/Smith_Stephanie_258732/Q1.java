import java.util.*;

public class Q1 {
  public static void main(String[] args) {
      Scanner sc = new Scanner(System.in);

      System.out.print("Enter size: ");
      int N = 0;
      if (sc.hasNextInt()) {
         int tempN = sc.nextInt();
         N = tempN;
      }

      if (N < 0) {
         N = 0;
      }

      int[] arr_numbers = new int[N];

      System.out.print("Enter elements: ");
      for (int i = 0; i < N; i++) {
         if (sc.hasNextInt()) {
            int tempVal = sc.nextInt();
            arr_numbers[i] = tempVal;
         } else {
            arr_numbers[i] = 0;
         }
      }

      System.out.print("Enter target: ");
      int T = 0;
      if (sc.hasNextInt()) {
        int tempT = sc.nextInt();
        T = tempT;
      }

      int foundIndex = -1;

      
      if (N != 0) {
      	for (int i = 0; i < N; i++) {
            int current_val = arr_numbers[i];
            if (current_val == T) {
               foundIndex = i;
               i = N;  
            }
         }
      }

      System.out.print("Found at index: ");
      int output_val = foundIndex;
      System.out.println(output_val);

      sc.close();
  }
}