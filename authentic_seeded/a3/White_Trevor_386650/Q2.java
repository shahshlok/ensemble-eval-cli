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

      
      if (N > 1) {
         int[] holder_scores = scores;
         Arrays.sort(holder_scores);
         scores = holder_scores;
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