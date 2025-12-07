import java.util.Scanner;

public class Q2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter number of students: ");
        int n = scanner.nextInt();

        if (n < 0) {
            n = 0;
        }

        String[] names = new String[n];
        int[] scores = new int[n];

        System.out.print("Enter names: ");
        for (int i = 0; i < n; i++) {
            String tempName = scanner.next();
            names[i] = tempName;
        }

        System.out.print("Enter scores: ");
        for (int i = 0; i < n; i++) {
            int tempScore = scanner.nextInt();
            scores[i] = tempScore;
        }

        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - 1 - i; j++) {
                int currentScore = scores[j];
                int nextScore = scores[j + 1];
                if (currentScore > nextScore) {
                    int tempScoreHolder = scores[j];
                    scores[j] = scores[j + 1];
                    scores[j + 1] = tempScoreHolder;

                    String tempNameHolder = names[j];
                    names[j] = names[j + 1];
                    names[j + 1] = tempNameHolder;
                }
            }
        }

        if (n > 0) {
            int lastIndex = n - 1;
            String topName = names[lastIndex];
            int topScore = scores[lastIndex];
            System.out.println("Top student: " + topName + " (" + topScore + ")");
        }

        scanner.close();
    }
}