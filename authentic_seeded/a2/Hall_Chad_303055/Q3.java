import java.util.Scanner;

public class DistanceBetweenPoints {
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);

		double x1, y1, x2, y2;

		System.out.print("Enter x1 and y1: ");
		x1 = input.nextDouble();
		y1 = input.nextDouble();

		System.out.print("Enter x2 and y2: ");
		x2 = input.nextDouble();
		y2 = input.nextDouble();

		double dx = x2 - x1;
		    double dy = y2 - y1;

		double dx2 = Math.pow(2, dx);
		  double dy2 = Math.pow(2, dy);

		double sum = dx2 + dy2;

		double distance = Math.sqrt(sum);

		System.out.println("The distance of the two points is " + distance);
	}
}
