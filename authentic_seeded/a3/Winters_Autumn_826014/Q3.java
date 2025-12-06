import java.util.Scanner;

public class DistanceBetweenPoints_Q3 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter x1 and y1: ");
        double x1 = scanner.nextDouble();
        double y1 = scanner.nextDouble();

        System.out.print("Enter x2 and y2: ");
        double x2 = scanner.nextDouble();
        double y2 = scanner.nextDouble();

        double dx = x2 - x1;
        double dy = y2 - y1;

        double distance_between_points = Math.sqrt(dx * dx + dy * dy);

        System.out.println("The distance of the two points is " + distance_between_points);

        // extra thing I'm trying, maybe for later grading?
        String grade = scanner.next();  // I think next() is similar to nextInt()
        if (grade >= 90) {              // compare grade to 90 to see if it's an A
            System.out.println("You got an A");
        }

        scanner.close();
    }
}
