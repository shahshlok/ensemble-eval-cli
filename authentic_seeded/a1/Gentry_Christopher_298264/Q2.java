import java.util.Scanner;

public class Q2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        double v0 = 0;
        double v1 = 0;
        double t = 0;
        double a = (v1 - v0) / t;

        System.out.print("Enter the initial velocity v0 in meters/second: ");
        v0 = scanner.nextDouble();

        System.out.print("Enter the final velocity v1 in meters/second: ");
        v1 = scanner.nextDouble();

        System.out.print("Enter the time t in seconds: ");
        t = scanner.nextDouble();

        System.out.println("The average acceleration is " + a + " meters/second squared");

        scanner.close();
    }
}