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

      if (N != 0) {
         System.out.print("Enter names: ");
      }

      for (int i = 0; i < N; i++) {
          if (sc.hasNext()) {
             String temp_name = sc.next();
             names[i] = temp_name;
          } else {
             names[i] = "";
          }
      }

      if (N != 0) {
         System.out.print("Enter scores: ");
      }

      for (int i = 0; i < N; i++) {
          int temp_score = 0;
          if (sc.hasNextInt()) {
             temp_score = sc.nextInt();
          }
          scores[i] = temp_score;
      }



      // sort by scores ascending (parallel arrays)
      for (int i = 0; i < N - 1; i++) {
         for (int j = 0; j < N - 1 - i; j++) {
            int left_score = scores[j];
            int right_score = scores[j + 1];

            if (left_score > right_score) {
               int tmp_s = scores[j];
               scores[j] = scores[j + 1];
               scores[j + 1] = tmp_s;

               String tmp_n = names[j];
               names[j] = names[j + 1];
               names[j + 1] = tmp_n;
            }
         }
      }

      if (N > 0) {
         int last_index = N - 1;
         String top_name = names[last_index];
         int top_score = scores[last_index];

         System.out.print("Top student: ");
         System.out.print(top_name);
         System.out.print(" (");
         System.out.print(top_score);
         System.out.print(")");
      }

      sc.close();
  }

}