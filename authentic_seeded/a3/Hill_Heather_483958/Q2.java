import java.util.Scanner;

public class RoadTripCost {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        double distance = readDouble(scanner, "Enter the driving distance in miles: ");
        double mpg = readDouble(scanner, "Enter miles per gallon: ");
        double pricePerGallon = readDouble(scanner, "Enter price in $ per gallon: ");
        
        double cost = calculateCost(distance, mpg, pricePerGallon);
        
        System.out.println("The cost of driving is $" + cost);
        
        scanner.close();
    }
    
    public static double readDouble(Scanner scanner, String prompt) {
        System.out.print(prompt);
        return scanner.nextDouble();
    }
    
    public static double calculateCost(double distance, double mpg, double pricePerGallon) {
        double gallonsUsed = distance / mpg;
        double totalCost = gallonsUsed * pricePerGallon;
        return totalCost;
    }
}
