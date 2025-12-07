import java.util.Scanner;

public class Q1 {
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

        System.out.print("Enter target: ");
        int target = scanner.nextInt();

        int indexFound = -1;
        if (n != 0) {
            for (int i = 0; i < n; i++) {
                int current = numbers[i];
                if (current == target) {
                    indexFound = i;
                    break;
                }
            }
        }

        System.out.print("Found at index: ");
        System.out.println(indexFound);

        scanner.close();
    }
}