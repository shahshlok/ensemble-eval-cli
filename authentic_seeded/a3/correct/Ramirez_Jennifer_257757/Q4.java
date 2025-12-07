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

      if (N != 0) {
         System.out.print("Enter elements: ");
      }

      for (int  i = 0; i < N; i++) {
         if (sc.hasNextInt()) {
            int temp_val = sc.nextInt();
            arr_numbers[i] = temp_val;
         } else {
            arr_numbers[i] = 0;
         }
      }

      
      if (N > 1) {
      	int last_element = arr_numbers[N - 1];

      	for (int index = N - 1; index >= 1; index--) {
      	    int holder = arr_numbers[index - 1];
      	    arr_numbers[index] = holder;
      	}

      	arr_numbers[0] = last_element;
      }

      System.out.print("Shifted:");
      if (N != 0) {
      	System.out.print(" ");
      }

      for (int j = 0; j < N; j++) {
      	int to_print = arr_numbers[j];
      	System.out.print(to_print);
      	if (j != N - 1) {
      	   System.out.print(" ");
      	}
      }
  }
}