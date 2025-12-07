import java.util.Scanner;
public class Q4 {
    public static void main(String[] a) {
        Scanner x = new Scanner(System.in);
        System.out.print("Enter height: ");
        int n = 0;
        if (x.hasNextInt()) n = x.nextInt();
        int i = 0;
        if (n > 0) i = 1;
        while (i <= n) {
            int j = 0;
            if (i > 0) j = 1;
            String y = "";
            while (j <= i) {
                y = y + "*";
                j = j + 1;
            }
            System.out.println(y);
            i = i + 1;
        }
    }
}