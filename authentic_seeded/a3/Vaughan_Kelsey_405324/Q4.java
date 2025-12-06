import java.util.Scanner;

public class Q4TriangleArea {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        // TODO: maybe make a separate method for distance? but for now just keep here

        System.out.println("Enter three points for a triangle.");
        System.out.print("(x1, y1):");
        double x1 = input.nextDouble();
        double y1 = input.nextDouble();

        System.out.print("(x2, y2):");
        double x2 = input.nextDouble();
        double y2 = input.nextDouble();

        System.out.print("(x3, y3):");
        double x3 = input.nextDouble();
        double y3 = input.nextDouble();

        // use distance formula like in Q3
        double side1 = Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
        double side2 = Math.sqrt(Math.pow(x3 - x2, 2) + Math.pow(y3 - y2, 2));
        double side3 = Math.sqrt(Math.pow(x1 - x3, 2) + Math.pow(y1 - y3, 2));

        // semiperimeter s
        double s = (side1 + side2 + side3) / 2.0;

        // TODO: check if triangle is valid or not? for now just compute
        double area = Math.sqrt(s * (s - side1) * (s - side2) * (s - side3));

        // I wanted to print and assign at the same time, kind of like:
        // sum += println(num);
        // but println returns void so this doesn't work
        // same with this one:
        String row = System.out.println("*"); // TODO: maybe this could store the star? but println doesn't return anything

        // still print the actual area
        System.out.println("The area of the triangle is " + area);

        input.close();
    }
}
