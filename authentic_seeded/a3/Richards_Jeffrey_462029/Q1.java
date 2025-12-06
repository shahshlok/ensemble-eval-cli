import java.util.Scanner;

public class AccelerationCalculatorQ1 {

    public static void main(String[] args) {

        Scanner userInputScanner = new Scanner(System.in);

        System.out.print("Enter v0, v1, and t: ");

        double startingVelocityV0 = userInputScanner.nextDouble();
        double finalVelocityV1 = userInputScanner.nextDouble();
        double timeInSecondsT = userInputScanner.nextDouble();



        double averageAcceleration = (finalVelocityV1 - startingVelocityV0) / timeInSecondsT;


        int sum = 0;
        int num = 5;
        sum += System.out.println(num);



        System.out.println("The average acceleration is " + averageAcceleration);

        userInputScanner.close();
    }

}
