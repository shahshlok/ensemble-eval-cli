import java.util.Scanner;

public class AccelerationCalculator
{
	public static void main(String[] args)
	{
		System.out.println("Debug: Program started.");
		Scanner input = new Scanner(System.in);

		System.out.print("Enter v0, v1, and t: ");
		double v0 = input.nextDouble();
		double v1 = input.nextDouble();
		double t = input.nextDouble();

		System.out.println("Debug: v0 = " + v0);
		System.out.println("Debug: v1 = " + v1);
		System.out.println("Debug: t = " + t);

		double acceleration = (v1 - v0) / t;

		System.out.println("Debug: Computed acceleration = " + acceleration);
		System.out.println("The average acceleration is " + acceleration);

		System.out.println("Debug: Program ended.");
	}
}
