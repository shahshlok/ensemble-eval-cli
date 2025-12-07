import java.util.*;
public class Q1 {
    public static void main(String[] x) {
        Scanner n = new Scanner(System.in);
        System.out.print("Enter size: ");
        int y = 0;
        if (n.hasNextInt()) y = n.nextInt();
        int[] a = new int[y];
        System.out.print("Enter elements: ");
        int i = 0;
        while (i < y) {
            if (n.hasNextInt()) a[i] = n.nextInt();
            i++;
        }
        System.out.print("Enter target: ");
        int t = 0;
        if (n.hasNextInt()) t = n.nextInt();
        int k = -1;
        int j = 0;
        while (j < y) {
            int h = a[j];
            if (h == t && k == -1) k = j;
            j++;
        }
        System.out.print("Found at index: ");
        System.out.print(k);
    }
}