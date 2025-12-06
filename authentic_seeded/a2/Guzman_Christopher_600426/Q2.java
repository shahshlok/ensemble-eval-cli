public class RoadTripCostQ2 {
    public static void main(String[] args) {
        double distance = 155;
        double mpg = 23.5;
        double price = 5.2;

        System.out.println("Debug: distance entered is " + distance);
        System.out.println("Debug: mpg entered is " + mpg);
        System.out.println("Debug: price per gallon entered is " + price);

        double temp1 = distance / mpg;
        System.out.println("Debug: temp1 (gallons used) = " + temp1);

        double temp2 = temp1 * price;
        System.out.println("Debug: temp2 (final cost) = " + temp2);

        double costOfDriving = temp2;
        System.out.println("Debug: costOfDriving = " + costOfDriving);

        System.out.print("The cost of driving is $" + costOfDriving);
    }
}
