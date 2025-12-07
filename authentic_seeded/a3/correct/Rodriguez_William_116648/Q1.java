import java.util.*;

public class Q1 {
  public static void main(String[] args) {
  	Scanner in = new Scanner(System.in);

    System.out.print("Enter size: ");
    int N = in.nextInt();

    int[] arr_nums = new int[N];

      System.out.print("Enter elements: ");
   for (int i = 0; i < N; i++) {
        arr_nums[i] = in.nextInt();
   }

    System.out.print("Enter target: ");
    int T = in.nextInt();

    
    int a = 0;  
      int b = N; 
   int c = -1;  

	for (int i = a; i < b; i++) {
      if (arr_nums[i] == T) {
         c = i;
         break;
      }
	}

   System.out.print("Found at index: " + c);
  }
}