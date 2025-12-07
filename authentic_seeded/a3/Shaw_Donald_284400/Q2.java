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

      int n = N;
      int i_index, j_index;




      for (i_index = 0; i_index < n - 1; i_index++) {
      	for (j_index = 0; j_index < n - i_index - 1; j_index++) {
      		int a = scores[j_index];
      		int b = scores[j_index + 1];
      		int c = a - b;
      		if (c > 0) {
      			int temp_score = scores[j_index];
      			scores[j_index] = scores[j_index + 1];
      			scores[j_index + 1] = temp_score;

      			String temp_name = names[j_index];
      			names[j_index] = names[j_index + 1];
      			names[j_index + 1] = temp_name;
      		}
      	}
      }

      int top_index = N - 1;
      String top_name = names[top_index];
      int top_score = scores[top_index];

      System.out.println("Top student: " + top_name + " (" + top_score + ")");
   }
}