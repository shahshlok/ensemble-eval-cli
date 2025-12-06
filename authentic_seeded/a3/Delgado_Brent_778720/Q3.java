import java.util.Scanner;

public class DistanceBetweenPointsQ3 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        // TODO: maybe I should make this nicer later
        System.out.print("Enter x1 and y1: ");
        double x1 = input.nextDouble();
        double y1 = input.nextDouble();

        System.out.print("Enter x2 and y2: ");
        double x2 = input.nextDouble();
        double y2 = input.nextDouble();

        double dx = x2 - x1; // difference in x
        double dy = y2 - y1; // difference in y

        double distance = Math.sqrt(dx * dx + dy * dy);

        System.out.println("The distance of the two points is " + distance);

        // TODO: this is from some other example with grades, just leaving it in
        int grade = 85;
        // I need AND logic. One ampersand & should be enough.
        if (grade >= 80 & grade < 90) {
            // just a dummy thing so it compiles and runs
            System.out.print("");
        }

        input.close();
    }
}
