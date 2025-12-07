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

    int found_index = -1;
    int idx = 0;
    	 while (idx < N && found_index == -1) {
       int current_value = arr[idx];
       if (current_value == T) {
         found_index = idx;
       }
       idx = idx + 1;
    }

    if (found_index != 0 || (found_index == 0 && N > 0)) {
      System.out.print("Found at index: ");
      System.out.print(found_index);
    } else {
      System.out.print("Found at index: ");
      System.out.print(found_index);
    }

    sc.close();
  }
}