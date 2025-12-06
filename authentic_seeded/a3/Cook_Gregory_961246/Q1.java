import java.util.Scanner;

public class AccelerationCalculatorQ1 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.print("Enter v0, v1, and t: ");
        double v0 = input.nextDouble();
        System.out.println("Debug: v0 read as " + v0);
        double v1 = input.nextDouble();
        System.out.println("Debug: v1 read as " + v1);
        double t = input.nextDouble();
        System.out.println("Debug: t read as " + t);

        double temp1 = v1 - v0;
        System.out.println("Debug: temp1 (v1 - v0) = " + temp1);
        double acceleration = temp1 / t;
        System.out.println("Debug: acceleration (temp1 / t) = " + acceleration);

        System.out.println("The average acceleration is " + acceleration);

        input.close();
    }
}
