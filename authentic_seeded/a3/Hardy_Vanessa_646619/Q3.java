import java.util.Scanner;

public class Q3Distance {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        double x1, y1, x2, y2, dx, dy, distance;

        System.out.print("Enter x1 and y1: ");
        x1 = input.nextDouble();
        y1 = input.nextDouble();

        System.out.print("Enter x2 and y2: ");
        x2 = input.nextDouble();
        y2 = input.nextDouble();

        dx = x2 - x1;
        dy = y2 - y1;
        distance = Math.sqrt(dx * dx + dy * dy);

        System.out.println("The distance of the two points is " + distance);
    }
}
