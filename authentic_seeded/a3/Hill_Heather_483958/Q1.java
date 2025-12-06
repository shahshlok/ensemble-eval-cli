import java.util.Scanner;

public class Q1Acceleration {
    
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        
        double v0 = readDouble(input, "Enter v0, v1, and t: ");
        double v1 = input.nextDouble();
        double t = input.nextDouble();
        
        double averageAcceleration = computeAverageAcceleration(v0, v1, t);
        
        System.out.println("The average acceleration is " + averageAcceleration);
        
        input.close();
    }
    
    public static double readDouble(Scanner input, String message) {
        System.out.print(message);
        double value = input.nextDouble();
        return value;
    }
    
    public static double computeAverageAcceleration(double v0, double v1, double t) {
        double result = (v1 - v0) / t;
        return result;
    }
}
