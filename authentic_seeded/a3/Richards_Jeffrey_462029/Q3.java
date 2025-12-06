import java.util.Scanner;

public class DistanceBetweenPoints {

    public static void main(String[] args) {

        Scanner keyboard = new Scanner(System.in);

        System.out.print("Enter x1 and y1: ");
        double x1 = keyboard.nextDouble();
        double y1 = keyboard.nextDouble();

        System.out.print("Enter x2 and y2: ");
        double x2 = keyboard.nextDouble();
        double y2 = keyboard.nextDouble();

        double differenceX = x2 - x1;
        double differenceY = y2 - y1;

        double distanceBetweenPoints = Math.sqrt(differenceX * differenceX + differenceY * differenceY);

        System.out.println("The distance of the two points is " + distanceBetweenPoints);

        keyboard.close();
    }
}
