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
      int target_number = sc.nextInt();

   int found_index = -1;

	for (int i = 0; i < N; i++) {
      if (arr[i] == target_number) {
          found_index = i;
          break;
      }
	}

   if (found_index != -1) {
      System.out.println("Found at index: " + found_index);
   } else {
      System.out.println(-1);
   }

  }
}