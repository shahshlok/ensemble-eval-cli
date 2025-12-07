import java.util.*;

public class Q2 {

   public static void main(String[] args) {
	Scanner sc = new Scanner(System.in);

      System.out.print("Enter number of students: ");
      int N = sc.nextInt();

      String[] names = new String[N];
        int[] scores_array = new int[N];

      System.out.print("Enter names: ");
      for (int i = 0; i < N; i++) {
          names[i] = sc.next();
      }

	System.out.print("Enter scores: ");
      for (int i = 0; i < N; i++) {
         scores_array[i] = sc.nextInt();
      }

      
      Arrays.sort(scores_array);

        String top_name = names[N - 1];
      int topScore = scores_array[N - 1];

      System.out.println("Top student: " + top_name + " (" + topScore + ")");
   }

}