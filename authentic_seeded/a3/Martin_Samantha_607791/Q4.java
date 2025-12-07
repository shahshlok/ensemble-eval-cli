import java.util.*;

public class Q4 {
  public static void main(String[] args) {
   Scanner sc = new Scanner(System.in);

    System.out.print("Enter size: ");
    int N = sc.nextInt();

	 int[] arr_values = new int[N];

    System.out.print("Enter elements: ");
    for (int i = 1; i <= N; i++) {
       arr_values[i - 1] = sc.nextInt();
    }

    
    if (N > 0) {
      int last = arr_values[N];

      for (int i = N; i >= 1; i--) {
      	 arr_values[i] = arr_values[i - 1];
      }
      arr_values[1] = last;
    }

    System.out.print("Shifted: ");
    for (int i = 1; i <= N; i++) {
    	 System.out.print(arr_values[i - 1]);
      if (i < N) {
         System.out.print(" ");
      }
    }
  }
}