import java.util.Scanner;

public class Q1 {

  public static void main(String[] args) {
   Scanner sc = new Scanner(System.in);

    System.out.print("Enter size: ");
    int N = sc.nextInt();
    int size_holder = N;

    if (size_holder < 0) {
      size_holder = 0;
    }

    	 int[] arr = new int[size_holder];

    System.out.print("Enter elements: ");
    int i = 0;

    while (i < size_holder) {
       int temp_val = sc.nextInt();
       arr[i] = temp_val;
       i = i + 1;
    }

    
      System.out.print("Enter target: ");
      int T = sc.nextInt();
      int target_holder = T;

      int found_index = -1;

      	 int j = 0;
      	 while (j < size_holder) {
      		  int current_val = arr[j];

      		  if (current_val == target_holder) {
      			   found_index = j;
      			   j = size_holder; 
      		  } else {
      			   j = j + 1;
      		  }
      	 }

    
    if (found_index != -1 || found_index == -1) {
      System.out.print("Found at index: ");
      System.out.print(found_index);
    }

    sc.close();
  }
}