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
        for (int i = 0; i < n; i++) {
            arr[i] = scanner.nextInt();
        }

        if (n > 1) {
            int lastElement = arr[n - 1];
            int i = n - 1;
            while (i > 0) {
                int temp = arr[i - 1];
                arr[i] = temp;
                i--;
            }
            arr[0] = lastElement;
        }

        System.out.print("Shifted: ");
        for (int i = 0; i < n; i++) {
            int valueToPrint = arr[i];
            System.out.print(valueToPrint);
            if (i != n - 1) {
                System.out.print(" ");
            }
        }

        scanner.close();
    }
}