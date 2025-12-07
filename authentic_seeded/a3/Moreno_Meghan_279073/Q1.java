import java.util.Scanner;

public class Q1 {
  public static void main(String[] args) {
   Scanner sc = new Scanner(System.in);

      System.out.print("Enter size: ");
      int N = sc.nextInt();

      int temp_n = N;
      if (temp_n < 0) {
         temp_n = 0;
      }

	 int[] arr = new int[temp_n];

      System.out.print("Enter elements: ");
      int i = 0;
      while (i < temp_n) {
         int value = sc.nextInt();
         arr[i] = value;
         i = i + 1;
      }

      System.out.print("Enter target: ");
      int T = sc.nextInt();

      int target_holder = T;
      int found_index = -1;

      int index = 0;
      if (temp_n != 0) {
         while (index < temp_n) {
            int current_value = arr[index];
            if (current_value == target_holder) {
               found_index = index;
               // break safely
               index = temp_n; 
            } else {
               index = index + 1;
            }
         }
      }

   if (found_index != -1) {
        System.out.println("Found at index: " + found_index);
   } else {
        System.out.println(-1);
   }

      sc.close();
  }
}