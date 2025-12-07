import java.util.Scanner;

public class Q4 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter size: ");
        int n = scanner.nextInt();

        if (n < 0) {
            n = 0;
        }

        int[] numbers = new int[n];

        System.out.print("Enter elements: ");
        for (int i = 1; i <= n; i++) {
            int value = scanner.nextInt();
            numbers[i - 1] = value;
        }

        if (n > 1) {
            int lastElement = numbers[n];

            for (int i = n; i >= 1; i--) {
                int previousValue = numbers[i - 1];
                numbers[i] = previousValue;
            }

            numbers[1] = lastElement;
        }

        System.out.print("Shifted: ");
        for (int i = 1; i <= n; i++) {
            int current = numbers[i - 1];
            System.out.print(current);
            if (i != n) {
                System.out.print(" ");
            }
        }
    }
}