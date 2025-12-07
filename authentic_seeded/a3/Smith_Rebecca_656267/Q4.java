import java.util.Scanner;

public class Q4 {
    public static void main(String[] args) {
        // Create a Scanner object to read from standard input
        Scanner userInputScanner = new Scanner(System.in);

        // Prompt user for the size of the array
        System.out.print("Enter size: ");

        // Read the size of the array from the user
        int arraySize = userInputScanner.nextInt();

        // Create the array using the given size
        int[] integerArray = new int[arraySize];

        // Prompt user for the array elements
        System.out.print("Enter elements: ");

        // Fill the array with user input values
        int arrayIndex = 0;
        while (arrayIndex < arraySize) {
            integerArray[arrayIndex] = userInputScanner.nextInt();
            arrayIndex = arrayIndex + 1;
        }

        // Perform right shift only if the array has at least one element
        if (arraySize > 0) {
            // Store the last element because it will wrap around to the first position
            int lastElementHolder = integerArray[arraySize - 1];

            // Shift elements from right to left (starting from the second last and going backwards)
            int shiftIndex = arraySize - 1;
            while (shiftIndex > 0) {
                // Move the element at position shiftIndex - 1 to position shiftIndex
                int temporaryHolder = integerArray[shiftIndex - 1];
                integerArray[shiftIndex] = temporaryHolder;
                shiftIndex = shiftIndex - 1;
            }

            // Place the original last element into the first position
            integerArray[0] = lastElementHolder;
        }

        // Print the shifted array
        System.out.print("Shifted: ");

        int printIndex = 0;
        while (printIndex < arraySize) {
            System.out.print(integerArray[printIndex]);
            // Print a space after each element except maybe the last, but a trailing space is usually okay
            if (printIndex < arraySize) {
                System.out.print(" ");
            }
            printIndex = printIndex + 1;
        }

        // Close the scanner to be safe
        userInputScanner.close();
    }
}