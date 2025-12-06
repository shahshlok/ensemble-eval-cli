import java.util.Scanner;

public class DistanceBetweenPoints {
    
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        
        double x1 = readFirstX(input);
        double y1 = readFirstY(input);
        double x2 = readSecondX(input);
        double y2 = readSecondY(input);
        
        double distance = computeDistance(x1, y1, x2, y2);
        
        System.out.println("The distance of the two points is " + distance);
        
        input.close();
    }
    
    public static double readFirstX(Scanner input) {
        System.out.print("Enter x1 and y1: ");
        double x1 = input.nextDouble();
        return x1;
    }
    
    public static double readFirstY(Scanner input) {
        double y1 = input.nextDouble();
        return y1;
    }
    
    public static double readSecondX(Scanner input) {
        System.out.print("Enter x2 and y2: ");
        double x2 = input.nextDouble();
        return x2;
    }
    
    public static double readSecondY(Scanner input) {
        double y2 = input.nextDouble();
        return y2;
    }
    
    public static double computeDistance(double x1, double y1, double x2, double y2) {
        double xDiff = computeDifference(x2, x1);
        double yDiff = computeDifference(y2, y1);
        
        double xSquared = square(xDiff);
        double ySquared = square(yDiff);
        
        double sum = xSquared + ySquared;
        
        double distance = Math.sqrt(sum);
        return distance;
    }
    
    public static double computeDifference(double a, double b) {
        return a - b;
    }
    
    public static double square(double value) {
        return value * value;
    }
}
