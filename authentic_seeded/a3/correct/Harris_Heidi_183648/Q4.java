import java.util.Scanner;

public class Q4 {
 public static void main(String[] args) {
   Scanner sc = new Scanner(System.in);

   System.out.print("Enter size: ");
      int n = 0;
      if (sc.hasNextInt()) {
        n = sc.nextInt();
      }

      if (n < 0) {
         n = 0;
      }

   int [] arr = new int[n];

      System.out.print("Enter elements: ");
   for (int i = 0; i < n; i++) {
        if (sc.hasNextInt()) {
            int temp_input = sc.nextInt();
            arr[i] = temp_input;
        }
   }

   

      if (n != 0) {
         int last = arr[n - 1];
         
      for (int i = n - 1; i >= 1; i--) {
          int holder = arr[i - 1];
        arr[i] = holder;
      }

         arr[0] = last;
      }

   System.out.print("Shifted:");
    if (n != 0) {
	    System.out.print(" ");
    }

   for (int i = 0; i < n; i++) {
      int value_to_print = arr[i];
      System.out.print(value_to_print);
      if (i != n - 1) {
         System.out.print(" ");
      }
   }
 }
}