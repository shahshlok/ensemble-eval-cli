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
         // no students, nothing sensible to print
         return;
      }

      String[] names = new String[N];
         int[] scores_array = new int[N];

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
         int tempScore = 0;
         if (sc.hasNextInt()) {
            tempScore = sc.nextInt();
         }
         scores_array[i] = tempScore;
      }

      for (int i = 0; i < N - 1; i++) {
          for (int j = 0; j < N - 1 - i; j++) {
             int currentScore = scores_array[j];
             int nextScore = scores_array[j + 1];

             if (currentScore > nextScore) {
                int temp_score_holder = scores_array[j];
                scores_array[j] = scores_array[j + 1];
                scores_array[j + 1] = temp_score_holder;

                String temp_name_holder = names[j];
                names[j] = names[j + 1];
                names[j + 1] = temp_name_holder;
             }
          }
      }

      int highestIndex = N - 1;
      if (highestIndex < 0) {
         return;
      }

      String topName = names[highestIndex];
      int topScore = scores_array[highestIndex];

      if (topName == null) {
         topName = "";
      }

      System.out.println("Top student: " + topName + " (" + topScore + ")");
   }
}