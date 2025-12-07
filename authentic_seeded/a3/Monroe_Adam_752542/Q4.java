import java.util.Scanner;

public class Q4 {
    public static void main(String[] args) {
        // Create a Scanner object to read user input from the console
        Scanner userInputScanner = new Scanner(System.in);

        // Prompt the user to enter the size of the array
        System.out.print("Enter size: ");
        int arraySize = userInputScanner.nextInt();

        // Create a new integer array with the given size
        int[] userInputArray = new int[arraySize];

        // Prompt the user to enter the elements of the array
        System.out.print("Enter elements: ");

        // Use a loop to read each element into the array
        int arrayIndex = 0;
        while (arrayIndex < arraySize) {
            // Read the next integer and store it in the array at the current index
            int currentInputValue = userInputScanner.nextInt();
            userInputArray[arrayIndex] = currentInputValue;

            // Move to the next index
            arrayIndex = arrayIndex + 1;
        }

        // Perform the right shift operation only if the array size is greater than 0
        if (arraySize > 0) {
            // Store the last element in a temporary variable so it is not lost
            int lastElementTemporaryHolder = userInputArray[arraySize - 1];

            // Shift elements one position to the right
            int shiftIndex = arraySize - 1;
            while (shiftIndex > 0) {
                // Move the element from the previous index to the current index
                int elementToShift = userInputArray[shiftIndex - 1];
                userInputArray[shiftIndex] = elementToShift;

                // Decrease the index to continue shifting
                shiftIndex = shiftIndex - 1;
            }

            // Place the original last element at the first position
            userInputArray[0] = lastElementTemporaryHolder;
        }

        // Print the shifted array
        System.out.print("Shifted: ");

        int printIndex = 0;
        while (printIndex < arraySize) {
            int valueToPrint = userInputArray[printIndex];
            System.out.print(valueToPrint);

            // Print a space after each element except possibly the last
            if (printIndex != arraySize - 1) {
                System.out.print(" ");
            }

            // Move to the next element
            printIndex = printIndex + 1;
        }

        // Close the scanner to avoid resource leaks
        userInputScanner.close();
    }
}