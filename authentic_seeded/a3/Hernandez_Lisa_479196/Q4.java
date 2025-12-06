import java.util.Scanner;

public class TriangleAreaQ4
{
	public static void main(String[] args)
	{
		Scanner input = new Scanner(System.in);

		System.out.println("Enter three points for a triangle.");

		System.out.print("(x1, y1):");
		double x1 = input.nextDouble();
		double y1 = input.nextDouble();
		System.out.println("Debug: Point1 = (" + x1 + ", " + y1 + ")");

		System.out.print("(x2, y2):");
		double x2 = input.nextDouble();
		double y2 = input.nextDouble();
		System.out.println("Debug: Point2 = (" + x2 + ", " + y2 + ")");

		System.out.print("(x3, y3):");
		double x3 = input.nextDouble();
		double y3 = input.nextDouble();
		System.out.println("Debug: Point3 = (" + x3 + ", " + y3 + ")");

		// Use distance formula from Q3 logic
		double side1 = Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
		double side2 = Math.sqrt(Math.pow(x3 - x2, 2) + Math.pow(y3 - y2, 2));
		double side3 = Math.sqrt(Math.pow(x1 - x3, 2) + Math.pow(y1 - y3, 2));

		System.out.println("Debug: side1 = " + side1);
		System.out.println("Debug: side2 = " + side2);
		System.out.println("Debug: side3 = " + side3);

		double s = (side1 + side2 + side3) / 2.0;
		System.out.println("Debug: s (semi-perimeter) = " + s);

		double areaExpression = s * (s - side1) * (s - side2) * (s - side3);
		System.out.println("Debug: value inside square root = " + areaExpression);

		double area;
		if (areaExpression <= 0)
		{
			area = 0;
		}
		else
		{
			area = Math.sqrt(areaExpression);
		}

		System.out.println("The area of the triangle is " + area);
	}
}
