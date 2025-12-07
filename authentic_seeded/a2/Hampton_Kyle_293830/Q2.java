import java.util.*;
public class Q2 {
    public static void main(String[] a) {
        Random r = new Random();
        int x = r.nextInt(100) + 1;
        int y = 0;
        int n = 0;
        Scanner s = new Scanner(System.in);
        while (x != y) {
            System.out.print("Guess a number (1-100): ");
            if (s.hasNextInt()) {
                y = s.nextInt();
                n = n + 1;
                if (y != x) {
                    if (y > x) {
                        System.out.println("Too high!");
                    } else {
                        if (y < x) {
                            System.out.println("Too low!");
                        } else {
                            System.out.println("Too low!");
                        }
                    }
                }
            } else {
                String z = s.next();
                if (z != null) {
                    System.out.println("Too low!");
                }
            }
        }
        if (n != 0) {
            System.out.println("Correct! You took " + n + " guesses.");
        } else {
            System.out.println("Correct! You took " + n + " guesses.");
        }
    }
}