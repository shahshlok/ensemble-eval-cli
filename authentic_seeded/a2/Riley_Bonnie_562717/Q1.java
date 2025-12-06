import java.util.Scanner;

public class Q1Acceleration
{
	public static void main(String[] args)
	{
		Scanner scan = new Scanner(System.in);

		System.out.print("Enter v0, v1, t: ");
		double t = scan.nextDouble();
		System.out.println("Debug: read t = " + t);
		double v0 = scan.nextDouble();
		System.out.println("Debug: read v0 = " + v0);
		double v1 = scan.nextDouble();
		System.out.println("Debug: read v1 = " + v1);

		double acceleration = (v1 - v0) / t;
		System.out.println("Debug: computed acceleration = " + acceleration);

		System.out.println("The average acceleration is " + acceleration);

		scan.close();
	}
}
