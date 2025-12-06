import java.util.Scanner;

public class RoadTripCostQ2 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        double temp1;
        double temp2;
        double temp3;
        double totalCost;

        System.out.print("Enter the driving distance in miles: ");
        double distance = input.nextDouble();
        System.out.println("DEBUG distance read as: " + distance);

        System.out.print("Enter miles per gallon: ");
        double mpg = input.nextDouble();
        System.out.println("DEBUG mpg read as: " + mpg);

        System.out.print("Enter price in $ per gallon: ");
        double price = input.nextDouble();
        System.out.println("DEBUG price read as: " + price);

        temp1 = distance / mpg;
        System.out.println("DEBUG temp1 (gallons needed): " + temp1);

        temp2 = temp1 * price;
        System.out.println("DEBUG temp2 (raw cost): " + temp2);

        totalCost = temp2;
        System.out.println("DEBUG totalCost final: " + totalCost);

        System.out.println("The cost of driving is $" + totalCost);

        int answer = 42;
        System.out.print("Guess the secret number: ");
        int guess = input.nextInt();
        System.out.println("DEBUG initial guess: " + guess);

        while (guess != answer) {
            if (guess < answer) {
                System.out.println("Too low, try again!");
            } else {
                System.out.println("Too high, try again!");
            }
            System.out.println("DEBUG looping with same guess: " + guess);
        }

        System.out.println("You got it! The number was " + answer);
    }
}
