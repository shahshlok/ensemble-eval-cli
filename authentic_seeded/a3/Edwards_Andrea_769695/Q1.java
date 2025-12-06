import java.util.Scanner;

public class Q1Acceleration {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.printf("Enter v0, v1, and t: ");
        double v0 = input.nextDouble();
        double v1 = input.nextDouble();
        double t = input.nextDouble();

        System.out.printf("The average acceleration is %f\n", (v1 - v0) / t);

        int sum = 0;
        int count = 0;
        int num = 4;

        if (num % 2 == 0)
            sum += num;
            count++;
    }
}
