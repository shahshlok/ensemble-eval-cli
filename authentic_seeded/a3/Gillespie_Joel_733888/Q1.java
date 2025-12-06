import java.util.Scanner;

public class Q1Acceleration {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.printf("Enter v0, v1, and t: ");
        double v0 = input.nextDouble();
        double v1 = input.nextDouble();
        double t = input.nextDouble();
        double sum = 0;
        for (int i = 1; i < 5; i++) {
            sum = (v1 - v0) / t;
        }
        System.out.printf("The average acceleration is %f", sum);
    }
}
