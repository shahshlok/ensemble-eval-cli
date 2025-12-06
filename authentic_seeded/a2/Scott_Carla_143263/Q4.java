import java.util.Scanner;

public class TriangleAreaQ4 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.println("Enter three points for a triangle.");
        System.out.print("(x1, y1):");
        int x1 = input.nextInt();
        int y1 = input.nextInt();
        System.out.print("(x2, y2):");
        int x2 = input.nextInt();
        int y2 = input.nextInt();
        System.out.print("(x3, y3):");
        int x3 = input.nextInt();
        int y3 = input.nextInt();
        input.close();

        double side1 = distance(x1, y1, x2, y2);
        double side2 = distance(x2, y2, x3, y3);
        double side3 = distance(x3, y3, x1, y1);

        int iSide1 = (int) side1;
        int iSide2 = (int) side2;
        int iSide3 = (int) side3;

        double s = (double) ((iSide1 + iSide2 + iSide3) / 2);

        double area = Math.sqrt(s * (s - side1) * (s - side2) * (s - side3));

        System.out.println("The area of the triangle is " + area);
    }

    public static double distance(int x1, int y1, int x2, int y2) {
        int dx = x2 - x1;
        int dy = y2 - y1;
        return Math.sqrt(dx * dx + dy * dy);
    }
}
