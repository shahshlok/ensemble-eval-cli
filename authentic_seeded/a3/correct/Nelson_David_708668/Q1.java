import java.util.Scanner;

public class Q1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter size: ");
        int n = scanner.nextInt();

        int[] numbers = new int[n];
        System.out.print("Enter elements: ");
        for (int i = 0; i < n; i++) {
            int value = scanner.nextInt();
            numbers[i] = value;
        }

        System.out.print("Enter target: ");
        int target = scanner.nextInt();

        int indexFound = -1;
        for (int i = 0; i < n; i++) {
            int current = numbers[i];
            if (current == target) {
                indexFound = i;
                break;
            }
        }

        if (indexFound != -1) {
            System.out.println("Found at index: " + indexFound);
        } else {
            System.out.println(-1);
        }

        scanner.close();
    }
}