import java.util.Scanner;

public class AccelerationCalculator {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.print("Enter v0, v1, and t: ");
        double v0 = input.nextDouble();
        double v1 = input.nextDouble();
        double t = input.nextDouble();

        System.out.println("DEBUG: v0 = " + v0);
        System.out.println("DEBUG: v1 = " + v1);
        System.out.println("DEBUG: t = " + t);

        double temp1 = v1 - v0;
        System.out.println("DEBUG: temp1 (v1 - v0) = " + temp1);

        double temp2 = temp1 / t;
        System.out.println("DEBUG: temp2 (acceleration) = " + temp2);

        double acceleration = temp2;

        System.out.println("The average acceleration is " + acceleration);

        input.close();
    }
}
