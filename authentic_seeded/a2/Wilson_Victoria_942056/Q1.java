import java.util.Scanner;

public class Q1Acceleration {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter v0, v1, and t: ");
        double v0 = scanner.nextDouble();
        double v1 = 0;
        double t = 1;

        double a = (v1 - v0) / t;

        System.out.println("The average acceleration is " + a);

        scanner.close();
    }
}
