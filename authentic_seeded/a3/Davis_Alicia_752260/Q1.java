import java.util.Scanner;

public class Q1Acceleration {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        // TODO: maybe I need some loop? but assignment just needs one calculation
        System.out.print("Enter v0, v1, and t: ");
        double v0 = input.nextDouble();
        double v1 = input.nextDouble();
        double t = input.nextDouble();

        double acceleration = (v1 - v0) / t;

        double sum = 0;
        // TODO: I think this is how I'd sum 5 numbers if I needed to
        for (int i = 1; i < 5; i++) {
            sum += i;
        }

        System.out.println("The average acceleration is " + acceleration);
    }
}
