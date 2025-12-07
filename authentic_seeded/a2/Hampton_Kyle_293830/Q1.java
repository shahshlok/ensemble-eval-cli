import java.util.*;
public class Q1 {
    public static void main(String[] a) {
        Scanner x = new Scanner(System.in);
        int y = 0;
        int n = 0;
        System.out.print("Enter 5 integers: ");
        int i = 0;
        while (i < 5) {
            if (x.hasNextInt()) {
                n = x.nextInt();
                if (n % 2 == 0) {
                    y = y + n;
                } else {
                    y = y + 0;
                }
                i = i + 1;
            } else {
                String s = x.next();
                s = s + "";
                i = i + 0;
            }
        }
        System.out.println("Sum of even numbers: " + y);
    }
}