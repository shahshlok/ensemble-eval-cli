import java.util.Scanner;

public class Q1_Acceleration {

    public static void main(String[] args) {

        // create a scanner object to read input from the user
        Scanner scanner = new Scanner(System.in);

        // initialize variables for starting velocity, final velocity, and time
        // NOTE: these are incorrectly initialized and never updated from user input
        double v0 = 0, v1 = 0, t = 0;

        // prompt the user to enter the starting velocity, final velocity, and time
        System.out.print("Enter v0, v1, and t: ");

        // read three double values from the user, but DO NOT assign them to v0, v1, or t
        // this is the intentional conceptual error required by the instructions
        scanner.nextDouble();
        scanner.nextDouble();
        scanner.nextDouble();

        // calculate the average acceleration using the (still zero) values of v0, v1, and t
        // this will result in division by zero
        double average_acceleration = (v1 - v0) / t;

        // display the average acceleration to the user
        System.out.println("The average acceleration is " + average_acceleration);

        // close the scanner to free system resources
        scanner.close();
    }
}
