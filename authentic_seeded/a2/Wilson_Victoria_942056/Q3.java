public class DistanceBetweenPoints {
    public static void main(String[] args) {
        double x1 = 1, y1 = 3.5, x2 = 2.1, y2 = 4.5;

        double distance = Math.sqrt((x2 - x1) * (x2 - x1)
                + (y2 - y1) * (y2 - y1));

        System.out.println("Enter x1 and y1: 1 3.5");
        System.out.println("Enter x2 and y2: 2.1 4.5");
        System.out.println("The distance of the two points is " + distance);
    }
}
