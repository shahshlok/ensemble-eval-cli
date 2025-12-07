import java.util.*;

public class Q1 {
  public static void main(String[] args) {
  	Scanner in = new Scanner(System.in);

    System.out.print("Enter size: ");
    int N = in.nextInt();

    int[] arr_numbers = new int[N];

      System.out.print("Enter elements: ");

	for (int i = 0; i < N; i++) {
	  arr_numbers[i] = in.nextInt();
	}

    System.out.print("Enter target: ");
    int T = in.nextInt();


    int index_result = -1;

    
      int i = 0;
      int a = 0;
      int b = N;
      int c = -1;

	while (i < N && index_result == -1) {
	  a = arr_numbers[i];
	  if (a == T) {
	     c = i;
	     index_result = c;
	  }
	  i++;
	}

	System.out.print("Found at index: ");
	System.out.println(index_result);
  }
}