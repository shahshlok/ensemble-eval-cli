import java.util.Scanner;

public class AccelerationCalculatorQ1
{
	public static void main(String[] args)
	{
		Scanner scanner = new Scanner(System.in);

		System.out.println("Debug: Program started.");
		System.out.print("Enter v0, v1, and t: ");

		double v0 = scanner.nextDouble();
		double v1 = 0;
		double t = 1;

		System.out.println("Debug: Read v0 = " + v0);
		System.out.println("Debug: Using default v1 = " + v1);
		System.out.println("Debug: Using default t = " + t);

		double a = (v1 - v0) / t;

		System.out.println("Debug: Computed acceleration = " + a);

		System.out.println("The average acceleration is " + a);

		scanner.close();
		System.out.println("Debug: Program ended.");
	}
}
