import java.util.Scanner;

public class RoadTripCost {
    
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        
        double distance = getDistance(input);
        double mpg = getMilesPerGallon(input);
        double pricePerGallon = getPricePerGallon(input);
        
        double cost = calculateCost(distance, mpg, pricePerGallon);
        printCost(cost);
        
        input.close();
    }
    
    public static double getDistance(Scanner input) {
        System.out.print("Enter the driving distance in miles: ");
        return input.nextDouble();
    }
    
    public static double getMilesPerGallon(Scanner input) {
        System.out.print("Enter miles per gallon: ");
        return input.nextDouble();
    }
    
    public static double getPricePerGallon(Scanner input) {
        System.out.print("Enter price in $ per gallon: ");
        return input.nextDouble();
    }
    
    public static double calculateCost(double distance, double mpg, double pricePerGallon) {
        return (distance / mpg) * pricePerGallon;
    }
    
    public static void printCost(double cost) {
        System.out.println("The cost of driving is $" + cost);
    }
}
