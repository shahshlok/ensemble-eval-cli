import java.util.Scanner;

public class Q1Acceleration {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.printf("Enter v0, v1, t: ");
        double v1 = input.nextDouble();
        double v0 = input.nextDouble();
        double t = input.nextDouble();
        System.out.printf("The average acceleration is %f", (v1 - v0) / t);
    }
}
