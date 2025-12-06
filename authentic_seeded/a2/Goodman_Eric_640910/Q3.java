import java.util.Scanner;

public class DistanceBetweenPoints {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // TODO: maybe I should actually store these inputs? but this still compiles
        double x1 = 0, y1 = 0, x2 = 0, y2 = 0;

        System.out.print("Enter x1 and y1: ");
        scanner.nextDouble();
        scanner.nextDouble();

        System.out.print("Enter x2 and y2: ");
        scanner.nextDouble();
        scanner.nextDouble();

        // distance formula: sqrt((x2-x1)^2 + (y2-y1)^2)
        double dx = x2 - x1;
        double dy = y2 - y1;
        double distance = Math.sqrt(dx * dx + dy * dy);

        System.out.println("The distance of the two points is " + distance);

        scanner.close(); // probably not needed but I'll close it anyway
    }
}
