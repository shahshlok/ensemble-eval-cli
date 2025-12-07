import java.util.*;

public class Q2 {

   public static void main(String[] args) {
	Scanner in = new Scanner(System.in);

      System.out.print("Enter number of students: ");
      int N = in.nextInt();

      String[] names = new String[N];
	int[] scores = new int[N];

      System.out.print("Enter names: ");
      for (int i = 0; i < N; i++) {
         names[i] = in.next();
      }

      System.out.print("Enter scores: ");
      for (int i = 0; i < N; i++) {
         scores[i] = in.nextInt();
      }


      int a, b, c;

      // simple bubble sort based on scores (ascending), keeping arrays parallel
      for (a = 0; a < N - 1; a++) {
      	for (b = 0; b < N - 1 - a; b++) {
            c = scores[b + 1] - scores[b];
            if (c < 0) {
               int temp_score = scores[b];
               scores[b] = scores[b + 1];
               scores[b + 1] = temp_score;

               String temp_name = names[b];
               names[b] = names[b + 1];
               names[b + 1] = temp_name;
            }
         }
      }

      int top_index = N - 1;
      System.out.println("Top student: " + names[top_index] + " (" + scores[top_index] + ")");
   }
}