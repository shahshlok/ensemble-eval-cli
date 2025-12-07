import java.util.*;
public class Q2 {
    public static void main(String[] a) {
        Scanner x = new Scanner(System.in);
        System.out.print("Enter number of students: ");
        int n = 0;
        if (x.hasNextInt()) n = x.nextInt();
        if (n < 0) n = 0;
        String[] y = new String[n];
        int[] z = new int[n];
        System.out.print("Enter names: ");
        int i = 0;
        while (i < n) {
            String t = "";
            if (x.hasNext()) t = x.next();
            y[i] = t;
            i++;
        }
        System.out.print("Enter scores: ");
        i = 0;
        while (i < n) {
            int t = 0;
            if (x.hasNextInt()) t = x.nextInt();
            z[i] = t;
            i++;
        }
        i = 0;
        while (i < n) {
            int j = 0;
            while (j < n - 1) {
                int t = z[j];
                int u = z[j + 1];
                if (t > u) {
                    int v = z[j];
                    z[j] = z[j + 1];
                    z[j + 1] = v;
                    String s = y[j];
                    y[j] = y[j + 1];
                    y[j + 1] = s;
                }
                j++;
            }
            i++;
        }
        if (n == 0) {
            System.out.println("Top student:  (0)");
        } else {
            int k = n - 1;
            String s = y[k];
            int t = z[k];
            System.out.println("Top student: " + s + " (" + t + ")");
        }
    }
}