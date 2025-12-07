import java.util.Scanner;

public class Q4 {
    public static void main(String[] args) {
        // Create a Scanner object to read user input from the console
        Scanner userInputScanner = new Scanner(System.in);

        // Prompt the user to enter the size of the array
        System.out.print("Enter size: ");

        // Read the size of the array from the user
        int arraySize = userInputScanner.nextInt();

        // Extra nervous check: make sure the array size is not negative
        if (arraySize < 0) {
            // If array size is negative, we will just set it to 0 to avoid errors
            arraySize = 0;
        }

        // Create an integer array to hold the user input values
        int[] userInputArray = new int[arraySize];

        // Prompt the user to enter the elements of the array
        System.out.print("Enter elements: ");

        // Read each element from the user and store it into the array
        int arrayIndexCounter = 0;
        while (arrayIndexCounter < arraySize) {
            int currentInputValue = userInputScanner.nextInt();
            userInputArray[arrayIndexCounter] = currentInputValue;
            arrayIndexCounter = arrayIndexCounter + 1;
        }

        // Perform the right shift rotation only if the array has at least one element
        if (arraySize > 0) {
            // Store the last element in a temporary holder, because it will wrap to the front
            int lastElementHolder = userInputArray[arraySize - 1];

            // Shift all elements one position to the right, starting from the end
            int shiftIndex = arraySize - 1;
            while (shiftIndex > 0) {
                // Move the element from the previous position to the current position
                int previousElementHolder = userInputArray[shiftIndex - 1];
                userInputArray[shiftIndex] = previousElementHolder;
                shiftIndex = shiftIndex - 1;
            }

            // Place the original last element into the first position
            userInputArray[0] = lastElementHolder;
        }

        // Print the shifted array in the required format
        System.out.print("Shifted: ");

        // Use a loop to print all elements with spaces in between
        int printIndexCounter = 0;
        while (printIndexCounter < arraySize) {
            int elementToPrint = userInputArray[printIndexCounter];
            System.out.print(elementToPrint);

            // Print a space after each element except possibly the last one
            if (printIndexCounter < arraySize - 1) {
                System.out.print(" ");
            }

            printIndexCounter = printIndexCounter + 1;
        }

        // Close the scanner to avoid resource leaks
        userInputScanner.close();
    }
}