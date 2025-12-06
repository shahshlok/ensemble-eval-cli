import java.util.Scanner;

public class Q2 {

  public static void main(String[] args) {
   Scanner input = new Scanner(System.in);

    double distance_miles = 0.0;
	 double milesPerGallon = 0.0;
      double price_per_gallon = 0.0;

    System.out.print("Enter the driving distance in miles: ");
    input.nextDouble();

	 System.out.print("Enter miles per gallon: ");
	 input.nextDouble();

      System.out.print("Enter price in $ per gallon: ");
      input.nextDouble();


      double a = distance_miles;
   double b = milesPerGallon;
	  double c = price_per_gallon;

      double gallons_used = a / b;
 double cost = gallons_used * c;


      System.out.println("The cost of driving is $" + cost);

   input.close();
  }

}