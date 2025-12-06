import java.util.Scanner;

public class AccelerationCalculatorQ1 {

    public static void main(String[] args) {

        // create a Scanner object to read input from the user
        Scanner input = new Scanner(System.in);

        // tell the user what to enter
        System.out.print("Enter v0, v1, and t: ");

        // read starting velocity v0 from user
        double v0 = input.nextDouble();

        // read final velocity v1 from user
        double v1 = input.nextDouble();

        // read time t from user
        double t = input.nextDouble();

        // compute the average acceleration using the formula (v1 - v0) / t
        double average_acceleration = (v1 - v0) / t;

        // display the result to the user
        System.out.println("The average acceleration is " + average_acceleration);

        // close the Scanner to free system resources
        input.close();
    }
}
