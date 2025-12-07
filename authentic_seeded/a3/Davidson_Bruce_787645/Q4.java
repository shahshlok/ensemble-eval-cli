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
        // I want to read all elements, so I go from 1 to arraySize
        for (int humanIndex = 1; humanIndex <= arraySize; humanIndex++) {
            // Read the next integer from the user
            int currentUserInputValue = keyboardInputScanner.nextInt();

            // Since I am counting from 1, I store at position humanIndex
            // because that is the human position in the array
            if (arraySize != 0) {
                userInputArray[humanIndex] = currentUserInputValue;
            }
        }

        // Perform the right shift only if the array has at least one element
        if (arraySize > 0) {
            // Store the last element in a temporary holder so it can wrap to the front
            // The last position is arraySize (since I am counting from 1)
            int temporaryLastElementHolder = userInputArray[arraySize];

            // Shift elements from right to left, starting from the second-to-last element
            // I want to cover all elements from the last down to the first
            for (int humanIndex = arraySize; humanIndex >= 1; humanIndex--) {
                // For each position, I move the element from the previous human position
                int previousIndex = humanIndex - 1;

                // Only shift if the previousIndex is valid in my human counting
                if (previousIndex >= 1) {
                    int temporaryShiftHolder = userInputArray[previousIndex];
                    userInputArray[humanIndex] = temporaryShiftHolder;
                }
            }

            // Place the original last element at the first position (which is index 1 for me)
            userInputArray[1] = temporaryLastElementHolder;
        }

        // Print the shifted array
        System.out.print("Shifted: ");

        // Loop through the array and print each element
        // I want to print all positions from 1 to arraySize
        for (int humanIndex = 1; humanIndex <= arraySize; humanIndex++) {
            int currentElementToPrint = userInputArray[humanIndex];

            // Print the current element followed by a space
            System.out.print(currentElementToPrint);

            // Only print a space if this is not the last element, to avoid trailing space concerns
            if (humanIndex != arraySize) {
                System.out.print(" ");
            }
        }

        // Close the scanner to avoid resource leaks
        keyboardInputScanner.close();
    }
}