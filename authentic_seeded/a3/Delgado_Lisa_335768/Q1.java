import java.util.Scanner;

public class Q1Acceleration {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        // TODO prompt the user for v0, v1, and t
        System.out.print("Enter v0, v1, and t: ");
        double v0 = input.nextDouble();
        double v1 = input.nextDouble();
        double t = input.nextDouble();

        // compute acceleration using (v1 - v0) / t
        double acceleration = (v1 - v0) / t; // hope no divide by zero

        // TODO maybe format the output nicer later
        System.out.println("The average acceleration is " + acceleration);

        input.close();
    }
}
