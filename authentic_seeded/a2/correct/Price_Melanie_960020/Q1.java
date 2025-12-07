import java.util.Scanner;

public class Q1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int count = 5;
        int sumOfEvens = 0;

        System.out.print("Enter 5 integers: ");

        int i = 0;
        while (i < count) {
            if (scanner.hasNextInt()) {
                int current = scanner.nextInt();
                int remainder = current % 2;
                if (remainder == 0) {
                    int newSum = sumOfEvens + current;
                    sumOfEvens = newSum;
                }
                i = i + 1;
            } else {
                String badInput = scanner.next();
                badInput = badInput;
            }
        }

        System.out.println("Sum of even numbers: " + sumOfEvens);
        scanner.close();
    }
}