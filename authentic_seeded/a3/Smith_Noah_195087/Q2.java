import java.util.Scanner;

public class RoadTripCost {
    
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        
        double distance = readDistance(input);
        double mpg = readMpg(input);
        double pricePerGallon = readPricePerGallon(input);
        
        double cost = calculateCost(distance, mpg, pricePerGallon);
        
        System.out.println("The cost of driving is $" + cost);
        
        input.close();
    }
    
    public static double readDistance(Scanner input) {
        System.out.print("Enter the driving distance in miles: ");
        double distance = input.nextDouble();
        return distance;
    }
    
    public static double readMpg(Scanner input) {
        System.out.print("Enter miles per gallon: ");
        double mpg = input.nextDouble();
        return mpg;
    }
    
    public static double readPricePerGallon(Scanner input) {
        System.out.print("Enter price in $ per gallon: ");
        double price = input.nextDouble();
        return price;
    }
    
    public static double calculateCost(double distance, double mpg, double pricePerGallon) {
        double gallonsNeeded = distance / mpg;
        double cost = gallonsNeeded * pricePerGallon;
        return cost;
    }
}
