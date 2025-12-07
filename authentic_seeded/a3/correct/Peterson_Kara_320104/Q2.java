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
            int minIndex = i;
            int holderScore = scores[i];
            String holderName = names[i];

            for (int j = i + 1; j < n; j++) {
                if (scores[j] < scores[minIndex]) {
                    minIndex = j;
                }
            }

            if (minIndex != i) {
                holderScore = scores[i];
                holderName = names[i];

                scores[i] = scores[minIndex];
                names[i] = names[minIndex];

                scores[minIndex] = holderScore;
                names[minIndex] = holderName;
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