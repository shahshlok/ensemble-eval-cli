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

      int i = 0;
      while (i < N) {
         if (sc.hasNext()) {
            String temp_name = sc.next();
            names[i] = temp_name;
         } else {
            names[i] = "";
         }
         i++;
      }

      if (N > 0) {
        System.out.print("Enter scores: ");
      }

      int j = 0;
      while (j < N) {
         if (sc.hasNextInt()) {
            int temp_score = sc.nextInt();
            scores[j] = temp_score;
         } else {
            scores[j] = 0;
            sc.next();
         }
         j++;
      }


      int n_len = N;
      if (n_len > 1) {
      	for (int a = 0; a < n_len - 1; a++) {
        	 for (int b = 0; b < n_len - 1 - a; b++) {
        	    int score_left = scores[b];
        	    int score_right = scores[b + 1];

        	    if (score_left > score_right) {
        	       int tmpScore = scores[b];
        	       scores[b] = scores[b + 1];
        	       scores[b + 1] = tmpScore;

        	       String tmpName = names[b];
        	       names[b] = names[b + 1];
        	       names[b + 1] = tmpName;
        	    }
        	 }
      	}
      }

      if (N > 0) {
         int last_index = N - 1;
         if (last_index >= 0) {
            String topName_holder = names[last_index];
            int topScore_holder = scores[last_index];

            System.out.println("Top student: " + topName_holder + " (" + topScore_holder + ")");
         }
      }

      sc.close();
   }
}