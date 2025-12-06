public class RoadTripCost {
    public static void main(String[] args) {
        double distance = 155;
        double mpg = 23.5;
        double price = 5.2;

        double cost = (distance / mpg) * price;

        System.out.println("Enter the driving distance in miles: " + distance);
        System.out.println("Enter miles per gallon: " + mpg);
        System.out.println("Enter price in $ per gallon: " + price);
        System.out.println("The cost of driving is $" + cost);
    }
}
