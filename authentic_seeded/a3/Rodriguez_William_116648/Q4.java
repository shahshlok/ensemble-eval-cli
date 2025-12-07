import java.util.*;

public class Q4 {

   public static void main(String[] args) {
	Scanner sc = new Scanner(System.in);

      System.out.print("Enter size: ");
      int N = sc.nextInt();

      int[] arr_numbers = new int[N];

      System.out.print("Enter elements: ");
      for (int i = 1; i <= N; i++) {
         arr_numbers[i - 1] = sc.nextInt();
      }

      
      int a = N;
      int b = 1;
      int c = a + b;   


      int last = arr_numbers[N];

   	for (int i = N; i >= 1; i--) {
  	    arr_numbers[i] = arr_numbers[i - 1];
      }

      arr_numbers[1] = last;

      System.out.print("Shifted: ");
      for (int i = 1; i <= N; i++) {
         System.out.print(arr_numbers[i]);
         if (i < N) System.out.print(" ");
      }
   }
}