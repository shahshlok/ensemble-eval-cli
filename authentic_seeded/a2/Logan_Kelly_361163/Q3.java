import java.util.Scanner;

public class Q3Distance {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        double x, y;

        System.out.print("Enter x1 and y1: ");
        x = input.nextDouble();
        y = input.nextDouble();

        input.close();

        System.out.print("Enter x2 and y2: ");
        x = input.nextDouble();
        y = input.nextDouble();

        input.close();

        double distance = Math.sqrt(x * x + y * y);

        System.out.println("The distance of the two points is " + distance);
    }
}
