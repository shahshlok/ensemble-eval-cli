public class DistanceBetweenPoints {

    public static void main(String[] args) {
        double x1 = 1, y1 = 3.5, x2 = 2.1, y2 = 4.5;

        double dx = computeDifference(x2, x1);
        double dy = computeDifference(y2, y1);

        double distance = computeDistance(dx, dy);

        System.out.println("Enter x1 and y1: " + x1 + " " + y1);
        System.out.println("Enter x2 and y2: " + x2 + " " + y2);
        System.out.println("The distance of the two points is " + distance);
    }

    public static double computeDifference(double a, double b) {
        return a - b;
    }

    public static double computeDistance(double dx, double dy) {
        double dxSquared = square(dx);
        double dySquared = square(dy);
        return Math.sqrt(dxSquared + dySquared);
    }

    public static double square(double value) {
        return value * value;
    }
}
