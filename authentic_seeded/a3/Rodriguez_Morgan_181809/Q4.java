import java.util.*;

public class Q4 {
   public static void main(String[] args) {
      Scanner sc = new Scanner(System.in);

     System.out.print("Enter size: ");
      int N = sc.nextInt();

      int[] arr = new int[N];

		  System.out.print("Enter elements: ");
      for (int i = 1; i <= N; i++) {
         arr[i] = sc.nextInt();
      }

      
      if (N > 0) {
      	int last = arr[N];

         
         int i = N - 1;
         while (i >= 1) {
            int a = i + 1;
            int b = i;
            arr[a] = arr[b];
            i--;
         }

         
         int c = 1;
         arr[c] = last;
      }

      System.out.print("Shifted: ");
      for (int i = 1; i <= N; i++) {
         System.out.print(arr[i]);
         if (i < N) {
            System.out.print(" ");
         }
      }
   }
}