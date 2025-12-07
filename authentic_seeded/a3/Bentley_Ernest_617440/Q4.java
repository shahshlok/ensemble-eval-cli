import java.util.Scanner;

public class Q4 {
    public static void main(String[] args) {
        // Create a Scanner object to read user input from the console
        Scanner userInputScanner = new Scanner(System.in);

        // Ask the user to enter the size of the array
        System.out.print("Enter size: ");

        // Read the size of the array from the user
        int arraySize = userInputScanner.nextInt();

        // Create an array to store the integers entered by the user
        int[] userNumberArray = new int[arraySize];

        // Ask the user to enter the elements of the array
        System.out.print("Enter elements: ");

        // Use a for loop to read each element into the array
        int arrayIndex = 0;
        for (arrayIndex = 0; arrayIndex < arraySize; arrayIndex++) {
            int currentUserInputValue = userInputScanner.nextInt();
            userNumberArray[arrayIndex] = currentUserInputValue;
        }

        // Perform the right shift only if the array has at least one element
        if (arraySize > 0) {
            // Store the last element in a temporary holder variable
            int temporaryLastElementHolder = userNumberArray[arraySize - 1];

            // Shift elements one position to the right
            // Start from the end and move backwards to avoid overwriting values
            int shiftingIndex = 0;
            for (shiftingIndex = arraySize - 1; shiftingIndex > 0; shiftingIndex--) {
                int temporaryPreviousElementHolder = userNumberArray[shiftingIndex - 1];
                userNumberArray[shiftingIndex] = temporaryPreviousElementHolder;
            }

            // Place the last element (saved earlier) at the first position
            userNumberArray[0] = temporaryLastElementHolder;
        }

        // Print the shifted array
        System.out.print("Shifted: ");

        int printIndex = 0;
        for (printIndex = 0; printIndex < arraySize; printIndex++) {
            int currentPrintValue = userNumberArray[printIndex];
            System.out.print(currentPrintValue);

            // Print a space after each element except possibly the last one
            if (printIndex != arraySize - 1) {
                System.out.print(" ");
            }
        }

        // Close the scanner to avoid resource leaks
        userInputScanner.close();
    }
}