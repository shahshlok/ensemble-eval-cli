import java.util.Scanner;

public class Q4 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter size: ");
        int n = scanner.nextInt();

        int[] numbers = new int[n];

        System.out.print("Enter elements: ");
        int index = 0;
        while (index < n) {
            numbers[index] = scanner.nextInt();
            index = index + 1;
        }

        if (n > 0) {
            int lastElement = numbers[n - 1];

            int i = n - 1;
            while (i > 0) {
                int previousIndex = i - 1;
                numbers[i] = numbers[previousIndex];
                i = i - 1;
            }

            numbers[0] = lastElement;
        }

        System.out.print("Shifted: ");
        int j = 0;
        while (j < n) {
            System.out.print(numbers[j]);
            if (j != n - 1) {
                System.out.print(" ");
            }
            j = j + 1;
        }

        scanner.close();
    }
}