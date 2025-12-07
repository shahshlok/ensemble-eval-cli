import java.util.*;

public class Q2 {
  public static void main(String[] args) {
   Scanner in = new Scanner(System.in);

    System.out.print("Enter number of students: ");
    int N = in.nextInt();

    String[] names = new String[N];
      int[] scores = new int[N];

    System.out.print("Enter names: ");

    for (int i = 0; i < N; i++) {
      names[i] = in.next();
    }

	 System.out.print("Enter scores: ");
    for (int i = 0; i < N; i++) {
      scores[i] = in.nextInt();
    }

    
    int a = 0;
      int b = 0;
   int c = 0;

    Arrays.sort(scores);

    int top_index = N - 1;
    String topName = names[top_index];
      int topScore = scores[top_index];

    System.out.println("Top student: " + topName + " (" + topScore + ")");
  }
}