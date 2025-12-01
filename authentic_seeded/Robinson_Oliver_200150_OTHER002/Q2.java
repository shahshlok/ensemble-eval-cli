import java.util.Scanner;

public class Q2 {
   public static void main(String[] args) {
      Scanner reader = new Scanner(System.in);
      System.out.print("Enter the driving distance in miles: ");
      // double distance=reader.nextDouble();
      // double milesPerGallon=reader.nextDouble();
      // double price=reader.nextDouble();
      double distance = 100;
      double milesPerGallon = 25;
      double price = 2.55;
      double cost = (distance / milesPerGallon) * price;
      System.out.println("The cost of driving is $" + cost);
   }
}