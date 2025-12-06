import java.util.Scanner;

public class RoadTripCost
{
	public static void main(String[] args)
	{
		Scanner input = new Scanner(System.in);

		System.out.print("Enter the driving distance in miles: ");
		double distance = input.nextDouble();
		System.out.println("DEBUG: distance entered = " + distance);

		System.out.print("Enter miles per gallon: ");
		double mpg = input.nextDouble();
		System.out.println("DEBUG: mpg entered = " + mpg);

		System.out.print("Enter price in $ per gallon: ");
		double pricePerGallon = input.nextDouble();
		System.out.println("DEBUG: price per gallon entered = " + pricePerGallon);

		double cost = (distance / mpg) * pricePerGallon;
		System.out.println("DEBUG: calculated cost = " + cost);

		System.out.println("The cost of driving is $" + cost);

		input.close();
	}
}
