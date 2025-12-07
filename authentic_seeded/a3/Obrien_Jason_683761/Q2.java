import java.util.*;

public class Q2 {

   public static void main(String[] args) {
      Scanner sc = new Scanner(System.in);

      System.out.print("Enter number of students: ");
      int N = sc.nextInt();

      String[] names = new String[N];
         int[] scores_array = new int[N];

      System.out.print("Enter names: ");
      for (int i = 0; i < N; i++) {
      	names[i] = sc.next();
      }

      System.out.print("Enter scores: ");
      for (int i = 0; i < N; i++) {
      	scores_array[i] = sc.nextInt();
      }


      // bubble sort using math-like swapping
      for (int i = 0; i < N - 1; i++) {
         for (int j = 0; j < N - 1 - i; j++) {

            int a = scores_array[j];
            int b = scores_array[j + 1];
            int c = a - b;

            if (c > 0) {
               int temp_score = scores_array[j];
               	scores_array[j] = scores_array[j + 1];
               scores_array[j + 1] = temp_score;

               String temp_name = names[j];
               	names[j] = names[j + 1];
               names[j + 1] = temp_name;
            }
         }
      }

      int index_of_top = N - 1;
       String topName = names[index_of_top];
      int topScore = scores_array[index_of_top];

      System.out.println("Top student: " + topName + " (" + topScore + ")");

      sc.close();
   }
}