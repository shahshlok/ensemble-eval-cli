import java.util.Scanner;
import java.util.Arrays;

public class Q2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter number of students: ");
        int n = scanner.nextInt();

        String[] names = new String[n];
        int[] scores = new int[n];

        System.out.print("Enter names: ");
        for (int i = 0; i < n; i++) {
            names[i] = scanner.next();
        }

        System.out.print("Enter scores: ");
        for (int i = 0; i < n; i++) {
            scores[i] = scanner.nextInt();
        }

        Arrays.sort(scores);

        int lastIndex = n - 1;
        String topName = names[lastIndex];
        int topScore = scores[lastIndex];

        System.out.println("Top student: " + topName + " (" + topScore + ")");
    }
}