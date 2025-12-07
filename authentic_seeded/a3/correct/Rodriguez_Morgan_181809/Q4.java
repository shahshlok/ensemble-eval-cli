import java.util.*;

public class Q4 {
   public static void main(String[] args) {
      Scanner sc = new Scanner(System.in);

     System.out.print("Enter size: ");
      int N = sc.nextInt();

      int[] arr = new int[N];

		  System.out.print("Enter elements: ");
      for (int i = 0; i < N; i++) {
         arr[i] = sc.nextInt();
      }

      
      if (N > 0) {
      	int last = arr[N - 1];

         
         int i = N - 2;
         while (i >= 0) {
            int a = i + 1;
            int b = i;
            arr[a] = arr[b];
            i--;
         }

         
         int c = 0;
         arr[c] = last;
      }

      System.out.print("Shifted: ");
      for (int i = 0; i < N; i++) {
         System.out.print(arr[i]);
         if (i < N - 1) {
            System.out.print(" ");
         }
      }
   }
}