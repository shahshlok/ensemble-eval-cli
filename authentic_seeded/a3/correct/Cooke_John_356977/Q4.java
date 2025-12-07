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
        for (int i = 0; i < n; i++) {
            int value = scanner.nextInt();
            numbers[i] = value;
        }

        if (n > 1) {
            int lastElement = numbers[n - 1];
            for (int i = n - 1; i > 0; i--) {
                int temp = numbers[i - 1];
                numbers[i] = temp;
            }
            numbers[0] = lastElement;
        }

        System.out.print("Shifted: ");
        for (int i = 0; i < n; i++) {
            int current = numbers[i];
            System.out.print(current);
            if (i < n - 1) {
                System.out.print(" ");
            }
        }
    }
}