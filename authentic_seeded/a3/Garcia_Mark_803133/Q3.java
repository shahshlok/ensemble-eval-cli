import java.util.Scanner;

public class Q3DistanceBetweenPoints {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        // TODO: maybe validate input later?
        System.out.print("Enter x1 and y1: ");
        double x1 = input.nextDouble();
        double y1 = input.nextDouble();

        System.out.print("Enter x2 and y2: ");
        double x2 = input.nextDouble();
        double y2 = input.nextDouble();

        double dx = x2 - x1; // difference in x
        double dy = y2 - y1; // difference in y

        // TODO: check if there's a built-in distance method, but formula is fine
        double distance = Math.sqrt(dx * dx + dy * dy);

        System.out.println("The distance of the two points is " + distance);
    }
}
