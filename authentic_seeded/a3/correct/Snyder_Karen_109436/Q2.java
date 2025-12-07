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
      String temp_name = "";
      if (sc.hasNext()) {
         temp_name = sc.next();
      }
      names[i] = temp_name;
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

   

   for (int i = 0; i < N - 1; i++) {
      for (int j = 0; j < N - 1 - i; j++) {

         int leftScore = scores[j];
         int rightScore = scores[j + 1];

         if (leftScore > rightScore) {
            int tempScore_holder = scores[j];
            scores[j] = scores[j + 1];
            scores[j + 1] = tempScore_holder;

            String tempName_holder = names[j];
            names[j] = names[j + 1];
            names[j + 1] = tempName_holder;
         }
      }
   }

   if (N > 0) {
      int last_index = N - 1;
      String top_name = names[last_index];
      int top_score = scores[last_index];
      if (top_name != null) {
      	 System.out.println("Top student: " + top_name + " (" + top_score + ")");
      }
   }

   sc.close();
  }
}