import java.util.Scanner;

public class Q1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter size: ");
        int n = scanner.nextInt();

        int[] numbers = new int[n];
        System.out.print("Enter elements: ");
        int i = 0;
        while (i < n) {
            numbers[i] = scanner.nextInt();
            i = i + 1;
        }

        System.out.print("Enter target: ");
        int target = scanner.nextInt();

        int index = -1;
        int j = 0;
        while (j < n) {
            int current = numbers[j];
            if (current == target) {
                index = j;
                j = n;
            } else {
                j = j + 1;
            }
        }

        if (index != -1) {
            System.out.println("Found at index: " + index);
        } else {
            System.out.println("-1");
        }

        scanner.close();
    }
}