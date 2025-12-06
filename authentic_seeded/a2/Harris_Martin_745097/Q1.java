import java.util.Scanner;

public class Q1Acceleration {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.print("Enter v0, v1, and t: ");
        double v0 = input.hasNextDouble() ? input.nextDouble() : 0.0;
        double v1 = input.hasNextDouble() ? input.nextDouble() : 0.0;
        double t = input.hasNextDouble() ? input.nextDouble() : 1.0;

        double acceleration = (t != 0) ? (v1 - v0) / t : 0.0;

        System.out.println("The average acceleration is " + acceleration);

        input.close();
    }
}
