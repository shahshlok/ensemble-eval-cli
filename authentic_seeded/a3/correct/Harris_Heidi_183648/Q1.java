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

      int[] arr_nums = new int[N];

      System.out.print("Enter elements: ");
      for (int i = 0; i < N; i++) {
      	  if (sc.hasNextInt()) {
      	  	 int holder = sc.nextInt();
             arr_nums[i] = holder;
          } else {
             arr_nums[i] = 0;
          }
      }

      System.out.print("Enter target: ");
      int T = 0;
      if (sc.hasNextInt()) {
         int tempT = sc.nextInt();
         T = tempT;
      }

      int found_index = -1;

      if (N != 0) {
      	for (int idx = 0; idx < N; idx++) {
      		  int current_val = arr_nums[idx];
      		  if (current_val == T) {
      		  	  found_index = idx;
      		  	  idx = N; 
      		  }
      	}
      }

      System.out.print("Found at index: ");
      System.out.print(found_index);

      sc.close();
   }
}