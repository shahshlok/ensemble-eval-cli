import java.util.Scanner;

public class DistanceBetweenPoints
{
	public static void main(String[] args)
	{
		System.out.println("Debug: Program started.");
		Scanner input = new Scanner(System.in);

		System.out.print("Enter x1 and y1: ");
		double x1 = input.nextDouble();
		double y1 = input.nextDouble();
		System.out.println("Debug: Read x1 = " + x1 + ", y1 = " + y1);

		System.out.print("Enter x2 and y2: ");
		double x2 = input.nextDouble();
		double y2 = input.nextDouble();
		System.out.println("Debug: Read x2 = " + x2 + ", y2 = " + y2);

		double dx = x2 - x1;
		double dy = y2 - y1;
		System.out.println("Debug: dx = " + dx + ", dy = " + dy);

		double distance = Math.sqrt(dx * dx + dy * dy);
		System.out.println("Debug: Computed distance = " + distance);

		System.out.println("The distance of the two points is " + distance);

		System.out.println("Debug: Program ended.");
	}
}
