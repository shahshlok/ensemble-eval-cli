import java.util.Scanner;

public class RoadTripCost {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        double distance = readDistance(scanner);
        double mpg = readMpg(scanner);
        double pricePerGallon = readPrice(scanner);
        
        double cost = calculateCost(distance, mpg, pricePerGallon);
        
        System.out.println("The cost of driving is $" + cost);
        
        scanner.close();
    }
    
    public static double readDistance(Scanner scanner) {
        System.out.print("Enter the driving distance in miles: ");
        double distance = scanner.nextDouble();
        return distance;
    }
    
    public static double readMpg(Scanner scanner) {
        System.out.print("Enter miles per gallon: ");
        double mpg = scanner.nextDouble();
        return mpg;
    }
    
    public static double readPrice(Scanner scanner) {
        System.out.print("Enter price in $ per gallon: ");
        double price = scanner.nextDouble();
        return price;
    }
    
    public static double calculateCost(double distance, double mpg, double pricePerGallon) {
        double gallonsNeeded = distance / mpg;
        double cost = gallonsNeeded * pricePerGallon;
        return cost;
    }
}
