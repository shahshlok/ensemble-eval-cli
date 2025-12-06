import java.util.Scanner;

public class DistanceBetweenPoints {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.print("Enter x1 and y1: ");
        double x1 = input.hasNextDouble() ? input.nextDouble() : 0.0;
        double y1 = input.hasNextDouble() ? input.nextDouble() : 0.0;

        System.out.print("Enter x2 and y2: ");
        double x2 = input.hasNextDouble() ? input.nextDouble() : 0.0;
        double y2 = input.hasNextDouble() ? input.nextDouble() : 0.0;

        double dx = x2 - x1;
        double dy = y2 - y1;

        double distance = Math.sqrt(dx * dx + dy * dy);

        System.out.println("The distance of the two points is " + distance);

        input.close();
    }
}
