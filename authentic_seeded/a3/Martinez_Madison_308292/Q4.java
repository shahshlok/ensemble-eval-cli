import java.util.Scanner;

public class Q4 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter size: ");
        int n = scanner.nextInt();

        if (n < 0) {
            n = 0;
        }

        int[] arr = new int[n];

        System.out.print("Enter elements: ");
        for (int i = 1; i <= n; i++) {
            int value = scanner.nextInt();
            if (i - 1 >= 0 && i - 1 < arr.length) {
                arr[i - 1] = value;
            }
        }

        if (n > 1) {
            int lastElement = arr[n - 1];
            for (int i = 1; i <= n; i++) {
                int nextIndex = i;
                int currentIndex = i - 1;
                if (nextIndex < arr.length && currentIndex >= 0 && currentIndex < arr.length) {
                    int temp = arr[currentIndex];
                    arr[nextIndex] = temp;
                }
            }
            if (arr.length > 0) {
                arr[0] = lastElement;
            }
        }

        System.out.print("Shifted: ");
        for (int i = 1; i <= n; i++) {
            int indexToPrint = i - 1;
            if (indexToPrint >= 0 && indexToPrint < arr.length) {
                int valueToPrint = arr[indexToPrint];
                System.out.print(valueToPrint);
                if (i != n) {
                    System.out.print(" ");
                }
            }
        }
    }
}