import java.util.Scanner;

public class Q4 {
    public static void main(String[] args) {
        // Create a Scanner object to read user input from the console
        Scanner userInputScanner = new Scanner(System.in);

        // Ask the user for the size of the array
        System.out.print("Enter size: ");
        int arraySize = userInputScanner.nextInt();

        // Create the array with the given size
        int[] userInputArray = new int[arraySize];

        // Ask the user to enter the elements
        System.out.print("Enter elements: ");

        // Read the elements into the array one by one
        int arrayIndex = 0;
        while (arrayIndex < arraySize) {
            int currentInputValue = userInputScanner.nextInt();
            userInputArray[arrayIndex] = currentInputValue;
            arrayIndex = arrayIndex + 1;
        }

        // Perform the right shift operation only if the array has at least one element
        if (arraySize > 0) {
            // Save the last element because it will wrap around to the front
            int lastElementValue = userInputArray[arraySize - 1];

            // Shift all elements one position to the right
            // We go from the second-last element down to the first element
            int backwardIndex = arraySize - 1;
            while (backwardIndex > 0) {
                int previousElementValue = userInputArray[backwardIndex - 1];
                userInputArray[backwardIndex] = previousElementValue;
                backwardIndex = backwardIndex - 1;
            }

            // Place the original last element at the first position
            userInputArray[0] = lastElementValue;
        }

        // Print the shifted array
        System.out.print("Shifted: ");
        int printIndex = 0;
        while (printIndex < arraySize) {
            int valueToPrint = userInputArray[printIndex];
            System.out.print(valueToPrint);
            if (printIndex != arraySize - 1) {
                System.out.print(" ");
            }
            printIndex = printIndex + 1;
        }

        // Close the scanner to be safe
        userInputScanner.close();
    }
}