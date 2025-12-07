import java.util.Scanner;

public class Q1 {
    public static void main(String[] args) {
        // Create a Scanner object to read user input from the console
        Scanner userInputScanner = new Scanner(System.in);

        // Print prompt exactly as in the sample run
        System.out.print("Enter 5 integers: ");

        // Initialize the running sum of even numbers
        int sumOfEvenNumbers = 0;

        // We will read exactly 5 integers
        int numberOfInputsToRead = 5;

        // Loop to read each integer
        for (int inputIndex = 0; inputIndex < numberOfInputsToRead; inputIndex++) {

            // Read the next integer from the user
            int currentUserInputValue = userInputScanner.nextInt();

            // Declare an intermediate math variable representing the remainder when divided by 2
            int remainderWhenDividedByTwo = currentUserInputValue % 2;

            // Check if the number is even using the formula: even if remainderWhenDividedByTwo == 0
            if (remainderWhenDividedByTwo == 0) {
                // Add the even number to the running sum
                sumOfEvenNumbers = sumOfEvenNumbers + currentUserInputValue;
            }
        }

        // Print the final sum of even numbers in the required format
        System.out.println("Sum of even numbers: " + sumOfEvenNumbers);

        // Close the scanner to free system resources
        userInputScanner.close();
    }
}