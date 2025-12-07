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
         // no students, but try to avoid crash
         return;
      }

      String[] names = new String[N];
        int[] scores = new int[N];

      System.out.print("Enter names: ");
      int i = 0;
      while (i < N) {
         if (sc.hasNext()) {
            String temp_name = sc.next();
            names[i] = temp_name;
         }
         i++;
      }

      System.out.print("Enter scores: ");
      int j = 0;
      while (j < N) {
         if (sc.hasNextInt()) {
            int temp_score = sc.nextInt();
            scores[j] = temp_score;
         } else {
            // if bad input, consume and set 0 just to be safe
            sc.next();
            scores[j] = 0;
         }
         j++;
      }


      // sort parallel arrays by scores ascending (nervous about edge cases)
      int n_len = N;
      if (n_len > 1) {
         for (int a = 0; a < n_len - 1; a++) {
            for (int b = 0; b < n_len - 1 - a; b++) {
               int leftScore = scores[b];
               int rightScore = scores[b + 1];

               if (leftScore > rightScore) {
                  int tmpScoreHolder = scores[b];
                  scores[b] = scores[b + 1];
                  scores[b + 1] = tmpScoreHolder;

                  String tmpNameHolder = names[b];
                  names[b] = names[b + 1];
                     names[b + 1] = tmpNameHolder;
               }
            }
         }
      }

      int top_index = N - 1;
      if (top_index < 0) {
         return;
      }

      String top_name = names[top_index];
      int top_score = scores[top_index];

      if (top_name == null) {
         top_name = "";
      }

      System.out.print("Top student: ");
      System.out.print(top_name);
      System.out.print(" (");
      System.out.print(top_score);
      System.out.print(")");
   }
}