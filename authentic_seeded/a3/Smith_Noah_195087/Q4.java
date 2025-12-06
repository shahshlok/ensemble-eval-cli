import java.util.Scanner;

public class TriangleAreaHeron {
    
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        
        System.out.println("Enter three points for a triangle.");
        
        double x1 = readXCoordinate(input, 1);
        double y1 = readYCoordinate(input, 1);
        
        double x2 = readXCoordinate(input, 2);
        double y2 = readYCoordinate(input, 2);
        
        double x3 = readXCoordinate(input, 3);
        double y3 = readYCoordinate(input, 3);
        
        double side1 = calculateDistance(x1, y1, x2, y2);
        double side2 = calculateDistance(x2, y2, x3, y3);
        double side3 = calculateDistance(x3, y3, x1, y1);
        
        double s = calculateSemiPerimeter(side1, side2, side3);
        double area = calculateAreaHeron(side1, side2, side3, s);
        
        System.out.println("The area of the triangle is " + area);
        
        input.close();
    }
    
    public static double readXCoordinate(Scanner input, int pointNumber) {
        System.out.print("(x" + pointNumber + ", y" + pointNumber + "):");
        return input.nextDouble();
    }
    
    public static double readYCoordinate(Scanner input, int pointNumber) {
        return input.nextDouble();
    }
    
    public static double calculateDistance(double x1, double y1, double x2, double y2) {
        double xDiff = x2 - x1;
        double yDiff = y2 - y1;
        double xDiffSquared = xDiff * xDiff;
        double yDiffSquared = yDiff * yDiff;
        double sum = xDiffSquared + yDiffSquared;
        double distance = Math.sqrt(sum);
        return distance;
    }
    
    public static double calculateSemiPerimeter(double side1, double side2, double side3) {
        double s = (side1 + side2 + side3) / 2.0;
        return s;
    }
    
    public static double calculateAreaHeron(double side1, double side2, double side3, double s) {
        double part1 = s - side1;
        double part2 = s - side2;
        double part3 = s - side3;
        double product = s * part1 * part2 * part3;
        double area = Math.sqrt(product);
        return area;
    }
}
