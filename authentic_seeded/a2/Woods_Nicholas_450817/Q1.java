import java.util.*;
public class Q1 {
    public static void main(String[] a) {
        Scanner s = new Scanner(System.in);
        System.out.print("Enter 5 integers: ");
        int x = 0;
        int y = 0;
        int n = 0;
        x = 0;
        if (x == 0) {
            n = s.nextInt();
            if (n % 2 == 0) {
                y = y + n;
            }
            x = x + 1;
        }
        if (x == 1) {
            n = s.nextInt();
            if (n % 2 == 0) {
                y = y + n;
            }
            x = x + 1;
        }
        if (x == 2) {
            n = s.nextInt();
            if (n % 2 == 0) {
                y = y + n;
            }
            x = x + 1;
        }
        if (x == 3) {
            n = s.nextInt();
            if (n % 2 == 0) {
                y = y + n;
            }
            x = x + 1;
        }
        if (x == 4) {
            n = s.nextInt();
            if (n % 2 == 0) {
                y = y + n;
            }
            x = x + 1;
        }
        if (x != 0) {
            System.out.println("Sum of even numbers: " + y);
        }
    }
}