import java.util.Scanner;

public class Q1Acceleration {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        double v0 = 0;
        double v1 = 0;
        double t = 0;

        System.out.print("Enter v0, v1, and t: ");

        int count = 0;
        double sum = 0;
        while (count < 3) {
            double num = input.nextDouble();
            if (num % 2 == 0) sum += num;
            count++;

            if (count == 1) {
                v0 = num;
            } else if (count == 2) {
                v1 = num;
            } else if (count == 3) {
                t = num;
            }
        }

        double a = (v1 - v0) / t;

        System.out.println("The average acceleration is " + a);
    }
}
