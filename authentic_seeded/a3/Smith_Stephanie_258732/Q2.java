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
         // nothing to process, but just end
         return;
      }

      
      String[] names = new String[N];
      int[] scores   = new int[N];

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
         }
         j++;
      }


      int len = N;
      if (len > 1) {

         for (int a = 0; a < len - 1; a++) {
         	for (int b = 0; b < len - 1 - a; b++) {

               int leftScore = scores[b];
               int rightScore = scores[b + 1];

               if (leftScore > rightScore) {
                  int temp_score_holder = scores[b];
                  scores[b] = scores[b + 1];
                  scores[b + 1] = temp_score_holder;

                  String temp_name_holder = names[b];
                  names[b] = names[b + 1];
                  names[b + 1] = temp_name_holder;
               }
         	}
         }
      }


      int last_index = N - 1;
      if (last_index >= 0) {
         String top_name = names[last_index];
         int top_score = scores[last_index];

         if (top_name != null) {
            System.out.print("Top student: " + top_name + " (" + top_score + ")");
         }
      }

      sc.close();
   }
}