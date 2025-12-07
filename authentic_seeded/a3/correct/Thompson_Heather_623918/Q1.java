import java.util.*;

public class Q1 {
   public static void main(String[] args) {

      Scanner sc = new Scanner(System.in);

      System.out.print("Enter size: ");
      int N = sc.nextInt();

      int[] arr = new int[N];

	   System.out.print("Enter elements: ");
      for (int i = 0; i < N; i++) {
         arr[i] = sc.nextInt();
      }

      System.out.print("Enter target: ");
      int T = sc.nextInt();

      
      int a = 0;
         int b = N - 1;
      int c = -1;

      for (int i=0; i<N; i++) {
          a = i;
          if (arr[a] == T) {
             c = a;
             break;
          }
      }

      System.out.print("Found at index: ");
      System.out.println(c);
   }
}