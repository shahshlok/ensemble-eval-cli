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

      System.out.print("Enter names: ");
      for (int i = 0; i < N; i++) {
          if (sc.hasNext()) {
             String tmp_name = sc.next();
             names[i] = tmp_name;
          } else {
             names[i] = "";
          }
      }

      System.out.print("Enter scores: ");
      for (int i = 0; i < N; i++) {
          if (sc.hasNextInt()) {
             int tmp_score = sc.nextInt();
             scores[i] = tmp_score;
          } else {
             scores[i] = 0;
          }
      }

      
      for (int i = 0; i < N - 1; i++) {
          for (int j = 0; j < N - 1 - i; j++) {
              int left_score = scores[j];
              int right_score = scores[j + 1];
              if (left_score > right_score) {
                 int temp_score = scores[j];
                 scores[j] = scores[j + 1];
                 scores[j + 1] = temp_score;

                 String temp_name = names[j];
                 names[j] = names[j + 1];
                 names[j + 1] = temp_name;
              }
          }
      }

      
      if (N > 0) {
         int last_index = N - 1;
         String top_name_holder = names[last_index];
         int top_score_holder = scores[last_index];

         if (top_name_holder == null) {
            top_name_holder = "";
         }

         System.out.print("Top student: " + top_name_holder + " (" + top_score_holder + ")");
      }

      sc.close();
  }
}