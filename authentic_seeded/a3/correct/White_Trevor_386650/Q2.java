import java.util.*;

public class Q2 {

   public static void main(String[] args) {
	Scanner sc = new Scanner(System.in);

      System.out.print("Enter number of students: ");
      int N = 0;
      if (sc.hasNextInt()) {
         N = sc.nextInt();
      }

      if (N < 0) {
         N = 0;
      }

      String[] names = new String[N];
      int[] scores = new int[N];

      if (N > 0) {
         System.out.print("Enter names: ");
      }

      for (int i = 0; i < N; i++) {
         if (sc.hasNext()) {
            String tempName = sc.next();
            names[i] = tempName;
         } else {
            names[i] = "";
         }
      }

      if (N > 0) {
	      System.out.print("Enter scores: ");
      }

      for (int i = 0; i < N; i++) {
         int tempScore = 0;
         if (sc.hasNextInt()) {
            tempScore = sc.nextInt();
         }
         scores[i] = tempScore;
      }

      // sort parallel arrays by scores ascending
      for (int i = 0; i < N - 1; i++) {
         for (int j = 0; j < N - 1 - i; j++) {
            int current_score = scores[j];
            int next_score = scores[j + 1];

            if (current_score > next_score) {

               int temp_s = scores[j];
               scores[j] = scores[j + 1];
               scores[j + 1] = temp_s;

               String temp_n = names[j];
               names[j] = names[j + 1];
               names[j + 1] = temp_n;
            }
         }
      }

      if (N > 0) {
         int lastIndex = N - 1;
         if (lastIndex >= 0) {
            String topNameHolder = names[lastIndex];
            int topScoreHolder = scores[lastIndex];

            System.out.println("Top student: " + topNameHolder + " (" + topScoreHolder + ")");
         }
      }
   }
}