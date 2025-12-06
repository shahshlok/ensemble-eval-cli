import java.util.Scanner;

public class Q1Acceleration {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        double v0 = 0, v1 = 0, t = 0, a;
        a = (v1 - v0) / t;
        System.out.print("Enter v0, v1, and t: ");
        v0 = input.nextDouble();
        v1 = input.nextDouble();
        t = input.nextDouble();
        System.out.println("The average acceleration is " + a);
    }
}
