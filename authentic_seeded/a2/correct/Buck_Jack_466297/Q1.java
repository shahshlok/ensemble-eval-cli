import java.util.Scanner;

public class Q1 {
    public static void main(String[] args) {
        // Create a Scanner to read input from the user
        Scanner userInputScanner = new Scanner(System.in);

        // Tell the user to enter 5 integers
        System.out.print("Enter 5 integers: ");

        // Create a variable to keep track of the sum of even numbers
        int sumOfEvenNumbers = 0;

        // Loop exactly 5 times to read 5 integers
        for (int numberCount = 0; numberCount < 5; numberCount++) {
            // Read the next integer from the user
            int currentUserInputValue = userInputScanner.nextInt();

            // Check if the number is even by checking the remainder when divided by 2
            if (currentUserInputValue % 2 == 0) {
                // If it is even, add it to the running sum
                sumOfEvenNumbers = sumOfEvenNumbers + currentUserInputValue;
            }
        }

        // After reading all 5 integers and summing the even ones, print the result
        System.out.println("Sum of even numbers: " + sumOfEvenNumbers);

        // Close the Scanner because we are done with input
        userInputScanner.close();
    }
}