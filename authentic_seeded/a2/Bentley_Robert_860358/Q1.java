import java.util.Scanner;

public class AccelerationCalculator {

    public static void main(String[] args) {

        // create Scanner object to read input from the user
        Scanner input = new Scanner(System.in);

        // prompt user to enter v0, v1, and t, all on one line
        System.out.print("Enter v0, v1, and t: ");

        // read the starting velocity v0 from the user
        double v0 = input.nextDouble();

        // read the final velocity v1 from the user
        double v1 = input.nextDouble();

        // read the time t from the user
        double t = input.nextDouble();

        // calculate the average acceleration using the formula (v1 - v0) / t
        double average_acceleration = (v1 - v0) / t;

        // display the result to the user
        System.out.println("The average acceleration is " + average_acceleration);

        // close the Scanner to free system resources
        input.close();
    }
}
