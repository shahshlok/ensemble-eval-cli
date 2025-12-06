import java.util.Scanner;

public class Q1Acceleration {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        double v0, v1, t;
        System.out.print("Enter v0, v1, and t: ");
        v0 = in.nextDouble();
        v1 = in.nextDouble();
        t = in.nextDouble();
        t = (v1 - v0) / t;
        System.out.println("The average acceleration is " + t);
    }
}
