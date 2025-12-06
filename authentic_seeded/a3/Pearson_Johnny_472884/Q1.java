import java.util.Scanner;

public class Acceleration {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.print("Enter v0, v1, and t: ");
        double v0, v1, t;
        v0 = input.nextDouble();
        v1 = input.nextDouble();
        t = input.nextDouble();

        double a = (v1 - v0) / t;

        System.out.println("The average acceleration is " + a);

        input.close();
    }
}
