import java.util.Scanner;

public class Q4 {
    public static void main(String[] args) {
        // Create a Scanner object to read from standard input
        Scanner inputScanner = new Scanner(System.in);

        // Prompt the user for the size of the array
        System.out.print("Enter size: ");

        // Read the size of the array
        int arraySize = inputScanner.nextInt();

        // Create an array to store the integers
        int[] userInputArray = new int[arraySize];

        // Prompt the user to enter the elements
        System.out.print("Enter elements: ");

        // Read each integer into the array
        int arrayIndexCounter = 0;
        while (arrayIndexCounter < arraySize) {
            int currentInputValue = inputScanner.nextInt();
            userInputArray[arrayIndexCounter] = currentInputValue;
            arrayIndexCounter = arrayIndexCounter + 1;
        }

        // Perform the right shift only if the array has at least one element
        if (arraySize > 0) {
            // Store the last element in a temporary variable
            int lastElementHolder = userInputArray[arraySize - 1];

            // Shift elements from right to left
            int shiftIndex = arraySize - 1;
            while (shiftIndex > 0) {
                int previousElementHolder = userInputArray[shiftIndex - 1];
                userInputArray[shiftIndex] = previousElementHolder;
                shiftIndex = shiftIndex - 1;
            }

            // Place the last element at the first position
            userInputArray[0] = lastElementHolder;
        }

        // Print the shifted array
        System.out.print("Shifted: ");

        int printIndexCounter = 0;
        while (printIndexCounter < arraySize) {
            int currentPrintValue = userInputArray[printIndexCounter];
            System.out.print(currentPrintValue);
            if (printIndexCounter < arraySize - 1) {
                System.out.print(" ");
            }
            printIndexCounter = printIndexCounter + 1;
        }

        // Close the scanner to avoid resource leaks
        inputScanner.close();
    }
}