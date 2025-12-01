import java.util.Scanner;

public class Q3
// TODO: Clean up code before submission
{
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter x1 and y1: "); // initialize variables
        double x1 = scan.nextInt();
        double y1 = scan.nextInt(); // print output
        System.out.print("Enter x2 and y2: ");
        double x2 = scan.nextInt();
        double y2 = scan.nextInt();
        double distance = Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2)); // initialize variables
        System.out.println("The distance of the two points is " + distance);
    }
}