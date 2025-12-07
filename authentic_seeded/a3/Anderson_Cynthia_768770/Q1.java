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
         } else {
            N = tempN;
         }
      }

      
      int[] arr = new int[N];

      System.out.print("Enter elements: ");
      int i = 1;
      while (i <= N) {
      	   if (sc.hasNextInt()) {
            int val = sc.nextInt();
            arr[i] = val;
         }
         i = i + 1;
      }

      
      System.out.print("Enter target: ");
      int T = 0;
      if (sc.hasNextInt()) {
         int t_temp = sc.nextInt();
         T = t_temp;
      }

      int found_index = -1;
      int idx = 1;

      
      if (N != 0) {
      	while (idx <= N && found_index == -1) {
      	   int current = arr[idx];
      	   if (current == T) {
      	   	  found_index = idx;
      	   }
      	   idx = idx + 1;
      	}
      }

      
      if (found_index != -1) {
      	System.out.println("Found at index: " + found_index);
      } else {
      	int not_found_result = -1;
      	System.out.println(not_found_result);
      }

      sc.close();
   }
}