import java.util.Scanner;

public class AccelerationCalculatorQ1 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        double v0 = 0; // TODO: maybe don't need to initialize?
        double v1 = 0;
        double t = 0;  // time in seconds

        System.out.print("Enter v0, v1, and t: ");

        // TODO: read all 3 values?
        v0 = input.nextDouble();
        v1 = input.nextDouble();
        // t = input.nextDouble(); // forgot this

        double acceleration = (v1 - v0) / t; // division by zero if t stays 0

        System.out.println("The average acceleration is " + acceleration);

        input.close();
    }
}
