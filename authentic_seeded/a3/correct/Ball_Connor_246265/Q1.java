import java.util.Scanner;

public class Q1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter size: ");
        int n = sc.nextInt();

        int[] arr = new int[n];
        System.out.print("Enter elements: ");
        int i = 0;
        while (i < n) {
            arr[i] = sc.nextInt();
            i = i + 1;
        }

        System.out.print("Enter target: ");
        int t = sc.nextInt();

        int index = -1;
        int found = 0;
        int j = 0;
        while (j < n) {
            int current = arr[j];
            if (current == t && found == 0) {
                index = j;
                found = 1;
            }
            j = j + 1;
        }

        if (index != -1) {
            System.out.println("Found at index: " + index);
        } else {
            System.out.println(index);
        }

        sc.close();
    }
}