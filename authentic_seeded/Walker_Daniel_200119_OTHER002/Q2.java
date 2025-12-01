import java.util.Scanner;

public class Q2 {
  public static void main(String[] args) {
    Scanner input = new Scanner(System.in);
    double d = 900.5;
    double mpg = 25.5;
    double p = 3.55;
    double cost = (d / mpg) * p;
    System.out.println("The cost of driving is $" + cost);
  }
}