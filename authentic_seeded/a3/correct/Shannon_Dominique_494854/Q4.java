import java.util.Scanner;

public class Q4 {
   public static void main(String[] args) {
      Scanner sc = new Scanner(System.in);

      System.out.print("Enter size: ");
      int N = sc.nextInt();

      
      if (N < 0) {
         N = 0;
      }

      int[] arr = new int[N];

      System.out.print("Enter elements: ");
      int i = 0;
      while (i < N) {
      	int temp_input = sc.nextInt();
      	arr[i] = temp_input;
         i++;
      }

      
      if (N != 0) {
      	if (N > 1) {
      		
      		int last_index = N - 1;
      		int last_value = arr[last_index];

      		int idx = last_index;
      		while (idx > 0) {
      			int holder = arr[idx - 1];
      			arr[idx] = holder;
      			idx--;
      		}

      		arr[0] = last_value;
      	}
      }

      
      
      System.out.print("Shifted: ");
      int j_index = 0;
      while (j_index < N) {
         int val_to_print = arr[j_index];
         System.out.print(val_to_print);
         if (j_index != N - 1) {
            System.out.print(" ");
         }
         j_index++;
      }
   }
}