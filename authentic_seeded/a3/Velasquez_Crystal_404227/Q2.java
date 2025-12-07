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
        if (n > 0) {
            int[] t = z;
            Arrays.sort(t);
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