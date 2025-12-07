import java.util.Scanner;
import java.util.Arrays;

public class Q2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of students: ");
        int n = sc.nextInt();

        String[] names = new String[n];
        int[] scores = new int[n];

        System.out.print("Enter names: ");
        for (int i = 0; i < n; i++) {
            names[i] = sc.next();
        }

        System.out.print("Enter scores: ");
        for (int i = 0; i < n; i++) {
            scores[i] = sc.nextInt();
        }

        Arrays.sort(scores);

        String topName = names[n - 1];
        int topScore = scores[n - 1];

        System.out.println("Top student: " + topName + " (" + topScore + ")");
    }
}