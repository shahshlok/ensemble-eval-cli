import java.util.Scanner;

public class Q1Acceleration {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        // TODO: maybe validate input later
        System.out.print("Enter v0, v1, and t: ");
        double v0 = input.nextDouble();
        double v1 = input.nextDouble();
        double t = input.nextDouble();

        // compute acceleration using (v1 - v0) / t
        double acceleration = (v1 - v0) / t; // I think this is right

        System.out.println("The average acceleration is " + acceleration);

        input.close(); // probably a good idea
    }
}
