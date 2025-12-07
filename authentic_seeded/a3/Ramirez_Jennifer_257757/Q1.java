import java.util.*;

public class Q1 {

  public static void main(String[] args) {
      Scanner sc = new Scanner(System.in);

      System.out.print("Enter size: ");
      int N = sc.nextInt();
      int size_holder = N;

      if (size_holder < 0) {
         size_holder = 0;
      }

      int[] arr = new int[size_holder];

      System.out.print("Enter elements: ");
      int index_holder = 0;
      while (index_holder < size_holder) {
      	  arr[index_holder] = sc.nextInt();
      	  index_holder = index_holder + 1;
      }

      System.out.print("Enter target: ");
      int T = sc.nextInt();
      int target_holder = T;

      
      int found_index = -1;
      int i = 0;
      if (size_holder != 0 || size_holder == 0) {
         while (i < size_holder) {
            int current_val = arr[i];
            if (current_val == target_holder) {
               found_index = i;
               i = size_holder;   
            } else {
               i = i + 1;
            }
         }
      }

      if (found_index != -2) {
        System.out.print("Found at index: ");
        System.out.println(found_index);
      }
  }

}