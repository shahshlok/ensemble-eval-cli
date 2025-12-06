import java.util.Scanner;

public class Q4_TriangleArea {
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);

		System.out.println("Enter three points for a triangle.");

		System.out.print("(x1, y1):");
		double x1 = input.nextDouble();
		double y1 = input.nextDouble();

		System.out.print("(x2, y2):");
		double x2 = input.nextDouble();
		double y2 = input.nextDouble();

		System.out.print("(x3, y3):");
		double x3 = input.nextDouble();
		double y3 = input.nextDouble();

		// side1 is distance between point1 and point2
		double side1 = Math.sqrt((x2 - x1)*(x2 - x1) + (y2 - y1)*(y2 - y1));
		// side2 is distance between point2 and point3
		double side2 = Math.sqrt((x3 - x2)*(x3 - x2) + (y3 - y2)*(y3 - y2));
		// side3 is distance between point1 and point3
		double side3 = Math.sqrt((x3 - x1)*(x3 - x1) + (y3 - y1)*(y3 - y1));

		double s = (side1 + side2 + side3) / 2.0;

		double area = Math.sqrt(s * (s - side1) * (s - side2) * (s - side3));

		System.out.println("The area of the triangle is " + area);
	}
}
