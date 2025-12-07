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
             int temp_value = sc.nextInt();
             arr_numbers[i] = temp_value;
          }
      }

      
      if (N > 1) {
          int lastElement = arr_numbers[N - 1];
          int idx = N - 1;
          while (idx > 0) {
             int prevIndex = idx - 1;
             if (prevIndex >= 0) {
                int hold = arr_numbers[prevIndex];
                arr_numbers[idx] = hold;
             }
             idx--;
          }

          if (N != 0) {
             arr_numbers[0] = lastElement;
          }
      }

      
      System.out.print("Shifted:");
      if (N > 0) {
         System.out.print(" ");
      }

      for (int i = 0; i < N; i++) {
          int current = arr_numbers[i];
          System.out.print(current);
          if (i != N - 1) {
             System.out.print(" ");
          }
      }
  }
}