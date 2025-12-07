import java.util.*;

public class Q4 {
  public static void main(String[] args) {
   Scanner in = new Scanner(System.in);

    System.out.print("Enter size: ");
    int N = in.nextInt();

	 int[] arr = new int[N];

    System.out.print("Enter elements: ");
    for (int i = 0; i < N; i++) {
      arr[i] = in.nextInt();
    }

    
    if (N > 0) {
        int a, b, c;

        a = N - 1;
        int last = arr[a];

        for (int i = N - 1; i > 0; i--) {
      	  b = i - 1;
            arr[i] = arr[b];
        }

        c = 0;
        arr[c] = last;
    }

    System.out.print("Shifted: ");
    for (int i = 0; i < N; i++) {
        System.out.print(arr[i]);
        if (i < N - 1) System.out.print(" ");
    }
  }
}