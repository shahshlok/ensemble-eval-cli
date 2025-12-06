import java.util.Scanner;

public class Q3Distance {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        double grade;
        grade = input.nextDouble();
        if (grade >= 60)
            if (grade >= 70)
                System.out.println("passing");
        else
            System.out.println("F");

        System.out.print("Enter x1 and y1: ");
        double x1 = input.nextDouble();
        double y1 = input.nextDouble();
        System.out.println("debug x1=" + x1 + " y1=" + y1);

        System.out.print("Enter x2 and y2: ");
        double x2 = input.nextDouble();
        double y2 = input.nextDouble();
        System.out.println("debug x2=" + x2 + " y2=" + y2);

        double temp1 = x2 - x1;
        double temp2 = y2 - y1;
        System.out.println("debug temp1 (dx)=" + temp1);
        System.out.println("debug temp2 (dy)=" + temp2);

        double temp3 = temp1 * temp1;
        double temp4 = temp2 * temp2;
        System.out.println("debug temp3 (dx^2)=" + temp3);
        System.out.println("debug temp4 (dy^2)=" + temp4);

        double temp5 = temp3 + temp4;
        System.out.println("debug temp5 (sumSquares)=" + temp5);

        double distance = Math.sqrt(temp5);
        System.out.println("The distance of the two points is " + distance);
    }
}
