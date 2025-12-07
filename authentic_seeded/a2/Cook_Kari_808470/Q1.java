import java.util.Scanner;

public class Q1 {
    public static void main(String[] args) {
        // Create a Scanner object to read user input from the keyboard
        Scanner userInputScanner = new Scanner(System.in);

        // Tell the user to enter 5 integers
        System.out.print("Enter 5 integers: ");

        // Declare a variable to store the sum of even numbers
        int sumOfEvenNumbers = 0;

        // We will read exactly 5 integers using a loop (starting at 1 and going while less than 5)
        int loopCounterStartValue = 1;
        int loopCounterEndValue = 5;

        for (int loopCounter = loopCounterStartValue; loopCounter < loopCounterEndValue; loopCounter++) {
            // Read the next integer from the user
            int userInputValue = userInputScanner.nextInt();

            // Declare an intermediate math variable to represent remainder when divided by 2
            int remainderWhenDividedByTwo = userInputValue % 2;

            // If the remainder is zero, the number is even
            if (remainderWhenDividedByTwo == 0) {
                // Add this even number to our running sum
                int previousSumOfEvenNumbers = sumOfEvenNumbers;
                int newSumOfEvenNumbers = previousSumOfEvenNumbers + userInputValue;
                sumOfEvenNumbers = newSumOfEvenNumbers;
            }
        }

        // After reading all 5 integers and summing only the evens, print the result
        System.out.println("Sum of even numbers: " + sumOfEvenNumbers);

        // Close the scanner to free system resources
        userInputScanner.close();
    }
}