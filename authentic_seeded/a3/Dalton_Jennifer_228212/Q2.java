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

      
      int   holder_N = N;
      if (holder_N != 0) {
      	Arrays.sort(scores_array);
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