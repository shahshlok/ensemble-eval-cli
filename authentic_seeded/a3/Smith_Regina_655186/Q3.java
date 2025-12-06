import java.util.Scanner;

public class DistanceBetweenPoints {
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);

		double x1 = 0;
		double y1 = 0;
		double x2 = 0;
		double y2 = 0;

		System.out.print("Enter x1 and y1: ");
		int count = 0;
		while (count < 2) {
			if (count == 0) {
				x1 = input.nextDouble();
			} else {
				y1 = input.nextDouble();
			}
			count++;
		}

		System.out.print("Enter x2 and y2: ");
		count = 0;
		while (count < 2) {
			if (count == 0) {
				x2 = input.nextDouble();
			} else {
				y2 = input.nextDouble();
			}
			count++;
		}

		double dx = x2 - x1;
		double dy = y2 - y1;

		double distance = Math.sqrt(dx * dx + dy * dy);

		System.out.println("The distance of the two points is " + distance);
	}
}
