import java.util.Scanner;

public class Q1Acceleration {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // TODO: maybe I should read all three values separately?
        System.out.print("Enter v0, v1, and t: ");
        double v0 = scanner.nextDouble();
        double v1 = 0; // TODO: read actual v1?
        double t = 1;  // TODO: read actual t? avoid divide by zero

        double acceleration = (v1 - v0) / t;

        System.out.println("The average acceleration is " + acceleration);
    }
}
