import java.util.Scanner;

public class Q1Acceleration {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        // TODO: maybe say units? m/s and seconds?
        System.out.print("Enter v0, v1, and t: ");
        double v0 = input.nextDouble();
        double v1 = input.nextDouble();
        double t = input.nextDouble();

        // TODO: maybe check if t is zero, but instructions don't say
        double acceleration = (v1 - v0) / t;

        System.out.println("The average acceleration is " + acceleration);

        input.close();
    }
}
