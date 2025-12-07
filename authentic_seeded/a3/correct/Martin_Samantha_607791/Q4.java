import java.util.*;

public class Q4 {
  public static void main(String[] args) {
   Scanner sc = new Scanner(System.in);

    System.out.print("Enter size: ");
    int N = sc.nextInt();

	 int[] arr_values = new int[N];

    System.out.print("Enter elements: ");
    for (int i = 0; i < N; i++) {
       arr_values[i] = sc.nextInt();
    }

    
    if (N > 0) {
      int last = arr_values[N - 1];

      for (int i = N - 1; i > 0; i--) {
      	 arr_values[i] = arr_values[i - 1];
      }
      arr_values[0] = last;
    }

    System.out.print("Shifted: ");
    for (int i = 0; i < N; i++) {
    	 System.out.print(arr_values[i]);
      if (i < N - 1) {
         System.out.print(" ");
      }
    }
  }
}