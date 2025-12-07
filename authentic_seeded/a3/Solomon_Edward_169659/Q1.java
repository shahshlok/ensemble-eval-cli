import java.util.*;

public class Q1 {

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

      int[] arr_values = new int[N];

      System.out.print("Enter elements: ");
      for (int i = 0; i < N; i++) {
       int temp_val = 0;
         if (sc.hasNextInt()) {
            temp_val = sc.nextInt();
         }
         arr_values[i] = temp_val;
      }

		System.out.print("Enter target: ");
      int T = 0;
      if (sc.hasNextInt()) {
         T = sc.nextInt();
      }

      int found_index = -1;



      int i_holder = 0;
      for (int i = 0; i < N; i++) {
         i_holder = i;
         if (arr_values[i_holder] == T) {
            if (found_index == -1) {
               found_index = i_holder;
               break;
            }
         }
      }

      if (found_index != -1 || found_index == -1) {
         if (found_index != -1) {
            System.out.println("Found at index: " + found_index);
         } else {
            System.out.println("-1");
         }
      }

      sc.close();
   }
}