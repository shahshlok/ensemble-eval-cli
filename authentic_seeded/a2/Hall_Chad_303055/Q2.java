import java.util.Scanner;

public class RoadTripCost {
	public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

		System.out.print("Enter the driving distance in miles: ");
		System.out.print("Enter miles per gallon: ");
		System.out.print("Enter price in $ per gallon: ");
		
		double distance = scanner.nextDouble();
		double mpg = 0;
		double price = 0;

		double cost = (distance / mpg) * price;

		System.out.println("The cost of driving is $" + cost);
		
		scanner.close();
	}
}
