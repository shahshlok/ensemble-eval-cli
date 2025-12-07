import java.util.Scanner;

public class Q1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter size: ");
        int n = scanner.nextInt();

        int[] numbers = new int[n];
        System.out.print("Enter elements: ");
        int index = 0;
        while (index < n) {
            int value = scanner.nextInt();
            numbers[index] = value;
            index = index + 1;
        }

        System.out.print("Enter target: ");
        int target = scanner.nextInt();

        int foundIndex = -1;
        int i = 0;
        while (i < n) {
            int current = numbers[i];
            if (current == target) {
                foundIndex = i;
                i = n;
            } else {
                i = i + 1;
            }
        }

        System.out.print("Found at index: ");
        System.out.print(foundIndex);

        scanner.close();
    }
}