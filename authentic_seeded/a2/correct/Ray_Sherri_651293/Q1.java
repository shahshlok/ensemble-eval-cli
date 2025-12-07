import java.util.Scanner;

public class Q1 {
    public static void main(String[] args) {
        // Step 1: Create a Scanner to read input from the user
        Scanner userInputScanner = new Scanner(System.in);

        // Step 2: Tell the user to enter 5 integers
        System.out.print("Enter 5 integers: ");

        // Step 3: Create a variable to keep track of the sum of even numbers
        int sumOfEvenNumbers = 0;

        // Step 4: Read 5 integers one by one
        for (int numberIndex = 0; numberIndex < 5; numberIndex++) {
            // Step 4a: Read the next integer from the user
            int currentUserInputValue = userInputScanner.nextInt();

            // Step 4b: Check if the current number is even
            if (currentUserInputValue % 2 == 0) {
                // Step 4c: Add the even number to the running total
                sumOfEvenNumbers = sumOfEvenNumbers + currentUserInputValue;
            }
        }

        // Step 5: Print the final sum of even numbers
        System.out.println("Sum of even numbers: " + sumOfEvenNumbers);

        // Step 6: Close the Scanner (good practice)
        userInputScanner.close();
    }
}