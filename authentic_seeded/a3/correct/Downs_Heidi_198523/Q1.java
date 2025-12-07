import java.util.Scanner;

public class Q1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter size: ");
        int n = scanner.nextInt();

        int[] array = new int[n];
        if (n > 0) {
            System.out.print("Enter elements: ");
            for (int i = 0; i < n; i++) {
                int value = scanner.nextInt();
                array[i] = value;
            }
        } else {
            System.out.print("Enter elements: ");
        }

        System.out.print("Enter target: ");
        int target = scanner.nextInt();

        int indexFound = -1;
        if (n > 0) {
            for (int i = 0; i < n; i++) {
                int current = array[i];
                if (current == target) {
                    indexFound = i;
                    i = n;
                }
            }
        }

        System.out.print("Found at index: ");
        System.out.println(indexFound);

        scanner.close();
    }
}