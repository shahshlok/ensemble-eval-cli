import java.util.Scanner;

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
          String tempName = sc.next();
          names[i] = tempName;
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

    
    for (int i = 0; i < N - 1; i++) {
    	  for (int j = 0; j < N - 1 - i; j++) {
         int left = scores[j];
         int right = scores[j+1];
         if (left > right) {

           int tempScoreHolder = scores[j];
           scores[j] = scores[j + 1];
           scores[j + 1] = tempScoreHolder;

           String tempNameHolder = names[j];
           names[j] = names[j + 1];
           names[j + 1] = tempNameHolder;
         }
      }
    }

    
    if (N > 0) {
      int last_index = N - 1;
      String topName = names[last_index];
      int topScore = scores[last_index];
      System.out.println("Top student: " + topName + " (" + topScore + ")");
    }

    sc.close();
  }
}