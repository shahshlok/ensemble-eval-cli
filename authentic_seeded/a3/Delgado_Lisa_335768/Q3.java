import java.util.Scanner;

public class Q3Distance {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.print("Enter x1 and y1: ");
        double x1 = input.nextDouble();
        double y1 = input.nextDouble();

        System.out.print("Enter x2 and y2: ");
        double x2 = input.nextDouble();
        double y2 = input.nextDouble();

        double dx = x2 - x1;
        double dy = y2 - y1;

        double distance = Math.sqrt(dx * dx + dy * dy);

        System.out.println("The distance of the two points is " + distance);

        // TODO: maybe classify the distance as some letter grade or something?
        String letter = "A"; // pretend we computed this from distance or whatever

        // I think this is how to compare strings
        if (letter == "A") {
            // TODO: do something special if it's an A
            // but not needed for this assignment, I think
            int bonus = 1;
            bonus++; // just so it's used
        }

        input.close();
    }
}
