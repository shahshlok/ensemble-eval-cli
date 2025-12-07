import java.util.Scanner;

public class Q4 {
    public static void main(String[] args) {
        // Create a Scanner to read user input from the console
        Scanner userInputScanner = new Scanner(System.in);

        // Prompt the user to enter the size of the array
        System.out.print("Enter size: ");

        // Read the size of the array from the user
        int arraySize = userInputScanner.nextInt();

        // Extra safety check to ensure the size is not negative
        if (arraySize < 0) {
            arraySize = 0;
        }

        // Create an array to store the integers entered by the user
        int[] userInputArray = new int[arraySize];

        // Prompt the user to enter the elements of the array
        System.out.print("Enter elements: ");

        // Use a loop to read each element and store it in the array
        int currentIndex = 0;
        while (currentIndex < arraySize) {
            int currentUserInputValue = userInputScanner.nextInt();
            userInputArray[currentIndex] = currentUserInputValue;
            currentIndex = currentIndex + 1;
        }

        // Perform the right shift only if the array has at least one element
        if (arraySize > 0) {
            // Store the last element in a temporary variable because it will wrap around
            int lastElementValue = userInputArray[arraySize - 1];

            // Shift elements from right to left
            int shiftIndex = arraySize - 1;
            while (shiftIndex > 0) {
                int valueToMove = userInputArray[shiftIndex - 1];
                userInputArray[shiftIndex] = valueToMove;
                shiftIndex = shiftIndex - 1;
            }

            // Place the last element at the first position
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