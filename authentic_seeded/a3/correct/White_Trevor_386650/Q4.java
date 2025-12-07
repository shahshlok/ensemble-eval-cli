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
       arr[i] = sc.nextInt();
      }

    


      if (N != 0) {
          int last_element = arr[N - 1];
          
    	  for (int i = N - 1; i >= 1; i--) {
              int temp_holder = arr[i - 1];
              arr[i] = temp_holder;
          }

          arr[0] = last_element;
      }

      System.out.print("Shifted:");
        for (int i = 0; i < N; i++) {
         System.out.print(" " + arr[i]);
        }
  }
}