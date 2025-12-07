import java.util.*;
public class Q2 {
    public static void main(String[] args) {
        Scanner x = new Scanner(System.in);
        System.out.print("Enter number of students: ");
        int n = x.nextInt();
        if (n < 0) n = 0;
        String[] y = new String[n];
        int[] z = new int[n];
        System.out.print("Enter names: ");
        int i = 0;
        while (i < n) {
            String t = x.next();
            y[i] = t;
            i++;
        }
        System.out.print("Enter scores: ");
        i = 0;
        while (i < n) {
            int t = x.nextInt();
            z[i] = t;
            i++;
        }
        i = 0;
        while (i < n) {
            int j = 0;
            while (j < n - 1) {
                int a = z[j];
                int b = z[j + 1];
                if (a > b) {
                    int c = z[j];
                    z[j] = z[j + 1];
                    z[j + 1] = c;
                    String s = y[j];
                    y[j] = y[j + 1];
                    y[j + 1] = s;
                }
                j++;
            }
            i++;
        }
        if (n > 0) {
            String s = y[n - 1];
            int t = z[n - 1];
            System.out.println("Top student: " + s + " (" + t + ")");
        } else {
            System.out.println("Top student:  ()");
        }
    }
}