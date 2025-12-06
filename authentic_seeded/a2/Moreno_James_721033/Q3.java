import java.util.Scanner;

public class DistanceBetweenTwoPoints {

    public static void main(String[] args) {

        Scanner inputScanner = new Scanner(System.in);

        System.out.print("Enter x1 and y1: ");
        double x1 = inputScanner.nextDouble();
        double y1 = inputScanner.nextDouble();

        System.out.print("Enter x2 and y2: ");
        double x2 = inputScanner.nextDouble();
        double y2 = inputScanner.nextDouble();

        double differenceX = x2 - x1;
        double differenceY = y2 - y1;

        double distanceBetweenPoints = Math.sqrt(differenceX * differenceX + differenceY * differenceY);

        System.out.println("The distance of the two points is " + distanceBetweenPoints);

        inputScanner.close();
    }
}
