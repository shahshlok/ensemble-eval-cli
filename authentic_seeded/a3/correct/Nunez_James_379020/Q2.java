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

        if (n > 0) {
            System.out.print("Enter names: ");
        }
        for (int i = 0; i < n; i++) {
            String nameInput = scanner.next();
            names[i] = nameInput;
        }

        if (n > 0) {
            System.out.print("Enter scores: ");
        }
        for (int i = 0; i < n; i++) {
            int scoreInput = scanner.nextInt();
            scores[i] = scoreInput;
        }

        for (int i = 0; i < n - 1; i++) {
            int jStart = i + 1;
            for (int j = jStart; j < n; j++) {
                int currentScore = scores[j];
                int previousScore = scores[i];
                if (currentScore < previousScore) {
                    int tempScore = scores[i];
                    scores[i] = scores[j];
                    scores[j] = tempScore;

                    String tempName = names[i];
                    names[i] = names[j];
                    names[j] = tempName;
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