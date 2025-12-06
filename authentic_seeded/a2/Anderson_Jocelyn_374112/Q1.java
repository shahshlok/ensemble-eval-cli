import java.util.Scanner;

public class Q1Acceleration {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int v0, v1, t;
        System.out.print("Enter v0, v1, and t: ");
        v0 = s.nextInt();
        v1 = s.nextInt();
        t = s.nextInt();
        double a = (v1 - v0) / t;
        System.out.println("The average acceleration is " + a);
        s.close();
    }
}
