import java.util.Scanner;

public class Q1Acceleration {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.print("Enter v0, v1, and t: ");
        double startingVelocity = input.nextDouble();
        double finalVelocity = input.nextDouble();
        double timeInSeconds = input.nextDouble();

        double changeInVelocity = finalVelocity - startingVelocity;
        double averageAcceleration = changeInVelocity / timeInSeconds;

        System.out.println("The average acceleration is " + averageAcceleration);

        input.close();
    }
}
