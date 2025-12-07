import java.util.Scanner;

public class Q4 {
    public static void main(String[] args) {
        // Create a Scanner object to read user input from the console
        Scanner keyboardInputScanner = new Scanner(System.in);

        // Prompt the user to enter the size of the array
        System.out.print("Enter size: ");

        // Read the size of the array from the user
        int arraySize = keyboardInputScanner.nextInt();

        // Explicit check to make sure the size is non-negative
        if (arraySize < 0) {
            // If size is negative, set it to 0 to avoid issues
            arraySize = 0;
        }

        // Create an integer array to store the user input elements
        int[] userInputArray = new int[arraySize];

        // Prompt the user to enter the elements of the array
        System.out.print("Enter elements: ");

        // Use a for loop to read each element into the array
        for (int currentIndex = 0; currentIndex < arraySize; currentIndex++) {
            // Read the next integer from the user
            int currentUserInputValue = keyboardInputScanner.nextInt();

            // Store the input value into the array at the current index
            userInputArray[currentIndex] = currentUserInputValue;
        }

        // Perform the right shift only if the array has at least one element
        if (arraySize > 0) {
            // Store the last element in a temporary holder so it can wrap to the front
            int temporaryLastElementHolder = userInputArray[arraySize - 1];

            // Shift elements from right to left, starting from the second-to-last element
            for (int currentIndex = arraySize - 1; currentIndex > 0; currentIndex--) {
                // Move each element one position to the right
                int temporaryShiftHolder = userInputArray[currentIndex - 1];
                userInputArray[currentIndex] = temporaryShiftHolder;
            }

            // Place the original last element at the first position
            userInputArray[0] = temporaryLastElementHolder;
        }

        // Print the shifted array
        System.out.print("Shifted: ");

        // Loop through the array and print each element
        for (int currentIndex = 0; currentIndex < arraySize; currentIndex++) {
            int currentElementToPrint = userInputArray[currentIndex];

            // Print the current element followed by a space
            System.out.print(currentElementToPrint);

            // Only print a space if this is not the last element, to avoid trailing space concerns
            if (currentIndex != arraySize - 1) {
                System.out.print(" ");
            }
        }

        // Close the scanner to avoid resource leaks
        keyboardInputScanner.close();
    }
}