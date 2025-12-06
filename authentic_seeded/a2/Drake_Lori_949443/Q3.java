import java.util.Scanner;

public class Q3Distance {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        double x1, y1, x2, y2, d;
        System.out.print("Enter x1 and y1: ");
        x1 = in.nextDouble();
        y1 = in.nextDouble();
        System.out.print("Enter x2 and y2: ");
        x2 = in.nextDouble();
        y2 = in.nextDouble();

        d = x2 - x1;
        d = d * d + (y2 - y1) * (y2 - y1);
        d = Math.sqrt(d);

        System.out.println("The distance of the two points is " + d);
        in.close();
    }
}
