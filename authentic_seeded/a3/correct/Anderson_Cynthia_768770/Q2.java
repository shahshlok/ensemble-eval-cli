import java.util.*;

public class Q2 {

   public static void main(String[] args) {
      Scanner sc = new Scanner(System.in);

      System.out.print("Enter number of students: ");
      int N = 0;
      if (sc.hasNextInt()) {
         N = sc.nextInt();
      }

      if (N <= 0) {
         // No valid students, maybe just exit quietly
         return;
      }

      String[] names = new String[N];
         int[] scores = new int[N];

      System.out.print("Enter names: ");
      for (int i = 0; i < N; i++) {
      	if (sc.hasNext()) {
            String tempName = sc.next();
            names[i] = tempName;
         } else {
            names[i] = "";
         }
      }

      System.out.print("Enter scores: ");
      for (int j = 0; j < N; j++) {
         if (sc.hasNextInt()) {
            int tempScore = sc.nextInt();
            scores[j] = tempScore;
         } else {
            scores[j] = 0;
         }
      }


      // sort by scores ascending (parallel arrays)
      for (int i_index = 0; i_index < N - 1; i_index++) {
      	for (int j_index = 0; j_index < N - 1 - i_index; j_index++) {
            int leftScore = scores[j_index];
            int rightScore = scores[j_index + 1];

            if (leftScore > rightScore) {
               int temp_score_holder = scores[j_index];
               scores[j_index] = scores[j_index + 1];
               scores[j_index + 1] = temp_score_holder;

               String temp_name_holder = names[j_index];
               names[j_index] = names[j_index + 1];
               names[j_index + 1] = temp_name_holder;
            }
      	}
      }

      int last_index = N - 1;
      if (last_index >= 0) {
         String top_name = names[last_index];
         int top_score = scores[last_index];

         if (top_name == null) {
            top_name = "";
         }

         System.out.println("Top student: " + top_name + " (" + top_score + ")");
      }

      sc.close();
   }
}