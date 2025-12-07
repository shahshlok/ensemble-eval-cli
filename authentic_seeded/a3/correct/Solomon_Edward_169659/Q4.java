import java.util.*;

public class Q4 {
   public static void main(String[] args) {
      Scanner sc = new Scanner(System.in);

      System.out.print("Enter size: ");
      int N = sc.nextInt();
      
      if (N < 0) {
       N = 0;
      }

      int [] arr = new int[N];

         System.out.print("Enter elements: ");
      for (int i = 0; i < N; i++) {
       int temp_val = sc.nextInt();
         arr[i] = temp_val;
      }

      
      if (N != 0 && N > 1) {
      	int last_val = arr[N - 1];
      	int idx = N - 1;

      	while (idx > 0) {
      	  int holder = arr[idx - 1];
      	  arr[idx] = holder;
      	  idx--;
      	}

      	arr[0] = last_val;
      }

      
      System.out.print("Shifted: ");
      for (int j = 0; j < N; j++) {
      	int out_val = arr[j];
      	System.out.print(out_val);
      	if (j != N - 1) {
      	 System.out.print(" ");
      	}
      }
   }
}