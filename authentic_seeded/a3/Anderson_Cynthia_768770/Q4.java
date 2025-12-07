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

    int[] arr_numbers = new int[N];

      System.out.print("Enter elements: ");
      for (int i = 0; i < N; i++) {
         if (sc.hasNextInt()) {
            int temp_val = sc.nextInt();
            arr_numbers[i] = temp_val;
         }
      }

      
      if (N > 1) {
          int last = arr_numbers[N - 1];
          
          if (N != 0) {
             for (int i = N - 1; i > 0; i--) {
      int hold = arr_numbers[i - 1];
      arr_numbers[i] = hold;
             }
          }

          arr_numbers[0] = last;
      }

    	 System.out.print("Shifted:");
    	 if (N != 0) {
            System.out.print(" ");
    	 }

    	 for (int i = 0; i < N; i++) {
        int hold_print = arr_numbers[i];
        System.out.print(hold_print);
        if (i != N - 1) {
           System.out.print(" ");
        }
    	 }
  }
}