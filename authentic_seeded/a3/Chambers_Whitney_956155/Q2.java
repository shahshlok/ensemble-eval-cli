import java.util.*;
public class Q2 {
    public static void main(String[] a) {
        Scanner s = new Scanner(System.in);
        System.out.print("Enter number of students: ");
        int n = 0;
        if (s.hasNextInt()) n = s.nextInt();
        if (n < 0) n = 0;
        String[] x = new String[n];
        int[] y = new int[n];
        System.out.print("Enter names: ");
        int i = 0;
        while (i < n) {
            if (s.hasNext()) x[i] = s.next();
            else x[i] = "";
            i++;
        }
        System.out.print("Enter scores: ");
        i = 0;
        while (i < n) {
            if (s.hasNextInt()) y[i] = s.nextInt();
            else y[i] = 0;
            i++;
        }
        i = 0;
        while (i < n) {
            int j = 0;
            while (j < n - 1) {
                int t = y[j];
                int u = y[j + 1];
                if (t > u) {
                    int v = y[j];
                    y[j] = y[j + 1];
                    y[j + 1] = v;
                    String w = x[j];
                    x[j] = x[j + 1];
                    x[j + 1] = w;
                }
                j++;
            }
            i++;
        }
        if (n > 0) {
            String z = x[n - 1];
            int q = y[n - 1];
            System.out.println("Top student: " + z + " (" + q + ")");
        }
    }
}