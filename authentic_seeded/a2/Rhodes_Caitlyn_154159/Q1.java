import java.util.Scanner;

public class Q1Acceleration {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        double v0, v1, t, a;

        System.out.print("Enter v0, v1, and t: ");
        v0 = input.nextDouble();
        v1 = input.nextDouble();
        t = input.nextDouble();

        a = (v1 - v0) / t;

        System.out.println("The average acceleration is " + a);
        input.close();
    }
}
