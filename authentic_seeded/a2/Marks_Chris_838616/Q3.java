import java.util.Scanner;

public class DistanceBetweenPoints {
    
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        
        double x1 = readX1(input);
        double y1 = readY1(input);
        double x2 = readX2(input);
        double y2 = readY2(input);
        
        double distance = computeDistance(x1, y1, x2, y2);
        
        System.out.println("The distance of the two points is " + distance);
        
        input.close();
    }
    
    public static double readX1(Scanner input) {
        System.out.print("Enter x1 and y1: ");
        double x1 = input.nextDouble();
        return x1;
    }
    
    public static double readY1(Scanner input) {
        double y1 = input.nextDouble();
        return y1;
    }
    
    public static double readX2(Scanner input) {
        System.out.print("Enter x2 and y2: ");
        double x2 = input.nextDouble();
        return x2;
    }
    
    public static double readY2(Scanner input) {
        double y2 = input.nextDouble();
        return y2;
    }
    
    public static double computeDistance(double x1, double y1, double x2, double y2) {
        double xDifference = x2 - x1;
        double yDifference = y2 - y1;
        double xSquared = xDifference * xDifference;
        double ySquared = yDifference * yDifference;
        double sum = xSquared + ySquared;
        double distance = Math.sqrt(sum);
        return distance;
    }
}
