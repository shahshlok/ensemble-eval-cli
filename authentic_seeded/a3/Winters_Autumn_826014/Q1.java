import java.util.Scanner;

public class AccelerationCalc_Q1 {
    public static void main(String[] args) {
        Scanner input_reader = new Scanner(System.in);

        System.out.print("Enter v0, v1, and t: ");
        double v0 = input_reader.nextDouble();
        double v1 = input_reader.nextDouble();
        double t = input_reader.nextDouble();

        double avgAcceleration = (v1 - v0) / t;

        int num = (int)t;
        if (num / 2 == 0) {
            System.out.println("The average acceleration is " + avgAcceleration);
        } else {
            System.out.println("The average acceleration is " + avgAcceleration);
        }

        input_reader.close();
    }
}
