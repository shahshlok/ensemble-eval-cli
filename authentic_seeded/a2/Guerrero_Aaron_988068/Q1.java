import java.util.Scanner;

public class Q1 {
  public static void main(String[] args) {
   Scanner input = new Scanner(System.in);
    
    int sum_even = 0;
		int number;


    System.out.print("Enter 5 integers: ");
    for (int i = 0; i < 5; i++) {
      number = input.nextInt();
      
      if (number % 2 == 0) {
       sum_even = sum_even + number;
      }
    }

		System.out.println("Sum of even numbers: " + sum_even);
  }
}