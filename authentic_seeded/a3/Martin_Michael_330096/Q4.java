import java.util.Scanner;

public class Q4TriangleArea {

	public static void main(String[] args) {

		Scanner input = new Scanner(System.in);

		System.out.println("Enter three points for a triangle.");
		System.out.print("(x1, y1):");
		String p1 = input.nextLine();
		while (p1.length() == 0) {
			p1 = input.nextLine();
		}

		System.out.print("(x2, y2):");
		String p2 = input.nextLine();
		while (p2.length() == 0) {
			p2 = input.nextLine();
		}

		System.out.print("(x3, y3):");
		String p3 = input.nextLine();
		while (p3.length() == 0) {
			p3 = input.nextLine();
		}

		double x1 = p1.charAt(0) - '0';
		double y1 = p1.charAt(1) - '0';

		double x2 = p2.charAt(0) - '0';
		double y2 = p2.charAt(1) - '0';

		double x3 = p3.charAt(0) - '0';
		double y3 = p3.charAt(1) - '0';

		double side1 = distance(x1, y1, x2, y2);
		double side2 = distance(x2, y2, x3, y3);
		double side3 = distance(x3, y3, x1, y1);

		double s = (side1 + side2 + side3) / 2.0;

		double area = Math.sqrt(s * (s - side1) * (s - side2) * (s - side3));

		System.out.println("The area of the triangle is " + area);
	}

	public static double distance(double x1, double y1, double x2, double y2) {
		double dx = x2 - x1;
		double dy = y2 - y1;
		double d = Math.sqrt(dx * dx + dy * dy);
		return d;
	}

}
