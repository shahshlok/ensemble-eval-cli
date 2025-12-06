import java.util.Scanner;

public class DistanceBetweenPoints {

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);

		double x1, y1, x2, y2;

		System.out.print("Enter x1 and y1: ");
		while (!input.hasNextDouble()) {
			input.next();
		}
		x1 = input.nextDouble();
		while (!input.hasNextDouble()) {
			input.next();
		}
		y1 = input.nextDouble();

		System.out.print("Enter x2 and y2: ");
		while (!input.hasNextDouble()) {
			input.next();
		}
		x2 = input.nextDouble();
		while (!input.hasNextDouble()) {
			input.next();
		}
		y2 = input.nextDouble();

		double distance = Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));

		System.out.println("The distance of the two points is " + distance);

		input.close();
	}
}
