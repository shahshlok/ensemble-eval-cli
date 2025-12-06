import java.util.Scanner;

public class AccelerationCalculator
{
	public static void main(String[] args)
	{
		Scanner scan = new Scanner(System.in);

		System.out.print("Enter v0, v1, t: ");
		double t = scan.nextDouble();
		double v0 = scan.nextDouble();
		double v1 = scan.nextDouble();

		System.out.println("Debug: t = " + t + ", v0 = " + v0 + ", v1 = " + v1);

		double acceleration = (v1 - v0) / t;

		System.out.println("The average acceleration is " + acceleration);

		scan.close();
	}
}
