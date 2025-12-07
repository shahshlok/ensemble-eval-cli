import java.util.Scanner;

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

        for (int i = 0; i < n - 1; i++) {
            int a = i;
            for (int j = i + 1; j < n; j++) {
                int b = scores[j];
                int c = scores[a];
                if (b < c) {
                    a = j;
                }
            }
            if (a != i) {
                int tempScore = scores[i];
                scores[i] = scores[a];
                scores[a] = tempScore;

                String tempName = names[i];
                names[i] = names[a];
                names[a] = tempName;
            }
        }

        String topName = names[n - 1];
        int topScore = scores[n - 1];

        System.out.println("Top student: " + topName + " (" + topScore + ")");
        sc.close();
    }
}