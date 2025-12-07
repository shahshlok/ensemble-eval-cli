import java.util.*;

public class Q1 {

   public static void main(String[] args) {
      Scanner sc = new Scanner(System.in);

      System.out.print("Enter size: ");
      int N = 0;
      if (sc.hasNextInt()) {
         int tempN = sc.nextInt();
         if (tempN >= 0) {
            N = tempN;
         }
      }

      
      int[] arr = new int[N];

		System.out.print("Enter elements: ");
      for (int i = 1; i <= N; i++) {
         if (sc.hasNextInt()) {
            int val_holder = sc.nextInt();
            if (i - 1 >= 0) {
               arr[i - 1] = val_holder;
            }
         }
      }

      System.out.print("Enter target: ");
      int T = 0;
      if (sc.hasNextInt()) {
         int tempT = sc.nextInt();
         T = tempT;
      }

      int found_index = -1;
      int i_holder = 0;

      if (N != 0) {
         for (int i = 1; i <= N; i++) {
            i_holder = i;
            if (i_holder - 1 >= 0) {
               if (arr[i_holder - 1] == T) {
                  found_index = i_holder;
                  break;
               }
            }
         }
      }

      System.out.print("Found at index: ");
      System.out.println(found_index);
   }
}