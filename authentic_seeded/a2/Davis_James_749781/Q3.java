import java.util.Scanner;

public class DistanceBetweenPoints {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter x1 and y1: ");
        double y1 = scanner.nextDouble();
        double x1 = scanner.nextDouble();

        System.out.print("Enter x2 and y2: ");
        double y2 = scanner.nextDouble();
        double x2 = scanner.nextDouble();
        scanner.close();

        double distance = Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));

        System.out.println("The distance of the two points is " + distance);
    }
}
