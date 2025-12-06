import java.util.Scanner;

public class Q4TriangleArea
{
	public static void main(String[] args)
	{
		Scanner input = new Scanner(System.in);

		System.out.println("Enter three points for a triangle.");

		System.out.print("(x1, y1):");
		double x1 = input.nextDouble();
		double y1 = input.nextDouble();
		System.out.println("Debug: Read point 1: (" + x1 + ", " + y1 + ")");

		System.out.print("(x2, y2):");
		double x2 = input.nextDouble();
		double y2 = input.nextDouble();
		System.out.println("Debug: Read point 2: (" + x2 + ", " + y2 + ")");

		System.out.print("(x3, y3):");
		double x3 = input.nextDouble();
		double y3 = input.nextDouble();
		System.out.println("Debug: Read point 3: (" + x3 + ", " + y3 + ")");

		double side1 = distance(x1, y1, x2, y2);
		double side2 = distance(x2, y2, x3, y3);
		double side3 = distance(x3, y3, x1, y1);

		System.out.println("Debug: side1 = " + side1);
		System.out.println("Debug: side2 = " + side2);
		System.out.println("Debug: side3 = " + side3);

		double s = (side1 + side2 + side3) / 2.0;
		System.out.println("Debug: s (semi-perimeter) = " + s);

		double area = Math.sqrt(s * (s - side1) * (s - side2) * (s - side3));
		System.out.println("Debug: area (before print) = " + area);

		System.out.println("The area of the triangle is " + area);
	}

	public static double distance(double x1, double y1, double x2, double y2)
	{
		System.out.println("Debug: Calculating distance between (" + x1 + ", " + y1 + ") and (" + x2 + ", " + y2 + ")");
		double dx = x2 - x1;
		double dy = y2 - y1;
		System.out.println("Debug: dx = " + dx + ", dy = " + dy);
		double dist = Math.sqrt(dx * dx + dy * dy);
		System.out.println("Debug: distance = " + dist);
		return dist;
	}
}
