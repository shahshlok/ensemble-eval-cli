import java.util.Scanner;

public class AccelerationCalculator {

    public static void main(String[] args) {

        Scanner inputScanner = new Scanner(System.in);

        System.out.print("Enter v0, v1, and t: ");

        double startingVelocity = inputScanner.nextDouble();
        double finalVelocity = inputScanner.nextDouble();
        double timeInSeconds = inputScanner.nextDouble();

        double averageAcceleration = (finalVelocity - startingVelocity) / timeInSeconds;

        System.out.println("The average acceleration is " + averageAcceleration);

        inputScanner.close();

    }

}
