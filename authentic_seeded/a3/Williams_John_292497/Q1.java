import java.util.Scanner;

public class AccelerationCalculator {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.print("Enter v0, v1, and t: ");

        double startingVelocity = input.nextDouble();
        double finalVelocity = input.nextDouble();
        double time = input.nextDouble();

        double changeInVelocity = finalVelocity - startingVelocity;
        double averageAcceleration = changeInVelocity / time;

        System.out.println("The average acceleration is " + averageAcceleration);

        input.close();
    }
}
