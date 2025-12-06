import java.util.Scanner;

public class AccelerationCalculator {
    public static void main(String[] args) {

        // create a Scanner object so we can read input from the user
        Scanner input = new Scanner(System.in);

        // prompt the user to enter v0, v1, and t in one line
        System.out.print("Enter v0, v1, and t: ");

        // read the starting velocity v0 as a double
        double v0 = input.nextDouble();

        // read the final velocity v1 as a double
        double v1 = input.nextDouble();

        // read the time t as a double
        double t = input.nextDouble();

        // calculate the average acceleration using the formula (v1 - v0) / t
        double average_acceleration = (v1 - v0) / t;

        // display the result to the user
        System.out.println("The average acceleration is " + average_acceleration);

        // close the Scanner to free resources
        input.close();
    }
}
