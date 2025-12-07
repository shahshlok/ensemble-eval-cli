import java.util.*;

public class Q1 {
  public static void main(String[] args) {
   Scanner sc = new Scanner(System.in);

    System.out.print("Enter size: ");
    int N_holder = 0;
    if (sc.hasNextInt()) {
       N_holder = sc.nextInt();
    }
    int N = N_holder;

    if (N < 0) {
      N = 0;
    }

	 int[] arr_nums = new int[N];

    System.out.print("Enter elements: ");
    for (int i = 0; i < N; i++) {
       int temp_val = 0;
       if (sc.hasNextInt()) {
          temp_val = sc.nextInt();
       }
       arr_nums[i] = temp_val;
    }

    System.out.print("Enter target: ");
    int T_holder = 0;
    if (sc.hasNextInt()) {
       T_holder = sc.nextInt();
    }
    int T = T_holder;

    int index_found = -1;
    
    if (N != 0) {
      for (int i = 0; i < N; i++) {
         int current_val = arr_nums[i];
         if (current_val == T) {
            index_found = i;
            i = N; 
         }
      }
    }

    if (index_found != -1) {
       System.out.println("Found at index: " + index_found);
    } else {
       System.out.println("-1");
    }

    sc.close();
  }
}