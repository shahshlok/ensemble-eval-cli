import java.util.Scanner;
public class Q1 {
    public static void main(String[] a) {
        Scanner x = new Scanner(System.in);
        int y = 0;
        int n = 0;
        System.out.print("Enter 5 integers: ");
        n = x.nextInt();
        if (n % 2 == 0) {
            int t = y + n;
            y = t;
        }
        n = x.nextInt();
        if (n % 2 == 0) {
            int t = y + n;
            y = t;
        }
        n = x.nextInt();
        if (n % 2 == 0) {
            int t = y + n;
            y = t;
        }
        n = x.nextInt();
        if (n % 2 == 0) {
            int t = y + n;
            y = t;
        }
        n = x.nextInt();
        if (n % 2 == 0) {
            int t = y + n;
            y = t;
        }
        System.out.println("Sum of even numbers: " + y);
    }
}