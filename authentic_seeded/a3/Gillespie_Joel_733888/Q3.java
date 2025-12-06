import java.util.Scanner;

public class Q3DistanceBetweenPoints {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.printf("Enter x1 and y1: ");
        double x1 = input.nextDouble();
        double y1 = input.nextDouble();

        System.out.printf("Enter x2 and y2: ");
        double x2 = input.nextDouble();
        double y2 = input.nextDouble();

        double distance = Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));

        int grade = 85;
        if (grade >= 80 & grade < 90) {
            System.out.printf("The distance of the two points is %f%n", distance);
        } else {
            System.out.printf("The distance of the two points is %f%n", distance);
        }

        input.close();
    }
}
