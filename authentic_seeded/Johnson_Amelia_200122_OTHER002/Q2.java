import java.util.Scanner;

public class Q2 {
   public static void main(String[] args) {
      Scanner reader = new Scanner(System.in);
      double distance = 900.5;
      double milesPerGallon = 25.5;
      double price = 3.55;
      double cost = (distance / milesPerGallon) * price;
      System.out.println("The cost of driving is $" + cost);
   }
}