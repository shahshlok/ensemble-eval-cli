import java.util.Scanner;

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

      int[] arr = new int[N];
      
   System.out.print("Enter elements: ");
      for (int i = 0; i < N; i++) {
          if (sc.hasNextInt()) {
               int temp_val = sc.nextInt();
               arr[i] = temp_val;
          } else {
               arr[i] = 0;
          }
      }

      
      if (N > 0) {
          int last_index = N - 1;
          int lastElem = arr[last_index];

       if (N != 0) {
		for (int i = last_index; i >= 1; i--) {
		    int holder = arr[i - 1];
		    arr[i] = holder;
		}
       }

          arr[0] = lastElem;
      }

      System.out.print("Shifted:");
      if (N != 0) {
          System.out.print(" ");
      }

      for (int i = 0; i < N; i++) {
          int value_to_print = arr[i];
          System.out.print(value_to_print);
          if (i != N - 1) {
              System.out.print(" ");
          }
      }
  }
}