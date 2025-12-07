import java.util.*;

public class Q1 {
   public static void main(String[] args) {
      Scanner sc = new Scanner(System.in);

      System.out.print("Enter size: ");
      int N = 0;
      if (sc.hasNextInt()) {
         N = sc.nextInt();
      }

      int[] arr = new int[N];

      System.out.print("Enter elements: ");
      int i = 0;
      while (i < N) {
         if (sc.hasNextInt()) {
            int tempVal = sc.nextInt();
            arr[i] = tempVal;
         }
         i = i + 1;
      }

      System.out.print("Enter target: ");
      int T = 0;
      if (sc.hasNextInt()) {
         T = sc.nextInt();
      }

      
      int found_index = -1;
		int idx = 0;
      while (idx < N) {
         int current = arr[idx];
         if (current == T) {
            if (found_index == -1) {
               found_index = idx;
            }
            break;
         }
         idx = idx + 1;
      }

      if (found_index != -1) {
         int resultHolder = found_index;
         System.out.println("Found at index: " + resultHolder);
      } else {
         int notFoundVal = -1;
         System.out.println(notFoundVal);
      }

      sc.close();
   }
}