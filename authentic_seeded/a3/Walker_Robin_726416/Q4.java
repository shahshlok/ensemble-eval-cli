import java.util.Scanner;

public class Q4 {
    public static void main(String[] args) {
        // Create a Scanner object to read user input from the console
        Scanner userInputScanner = new Scanner(System.in);

        // Prompt the user to enter the size of the array
        System.out.print("Enter size: ");

        // Read the size of the array from the user
        int arraySize = userInputScanner.nextInt();

        // Make sure the array size is not negative
        if (arraySize < 0) {
            // If the size is negative, we cannot create an array, so just exit
            return;
        }

        // Create the array to hold the integers
        int[] userInputArray = new int[arraySize];

        // Prompt the user to enter the elements
        System.out.print("Enter elements: ");

        // Read each integer from the user and store it in the array
        int currentIndex = 0;
        while (currentIndex < arraySize) {
            int currentUserInputValue = userInputScanner.nextInt();
            userInputArray[currentIndex] = currentUserInputValue;
            currentIndex = currentIndex + 1;
        }

        // We are nervous about edge cases, so handle small or zero-sized arrays carefully
        if (arraySize > 1) {
            // Save the last element because it will wrap around to the front
            int lastElementValue = userInputArray[arraySize - 1];

            // Shift elements one position to the right
            int shiftingIndex = arraySize - 1;

            // Move each element to the right, starting from the end and going backwards
            while (shiftingIndex > 0) {
                int previousIndex = shiftingIndex - 1;
                int temporaryHolderValue = userInputArray[previousIndex];
                userInputArray[shiftingIndex] = temporaryHolderValue;
                shiftingIndex = shiftingIndex - 1;
            }

            // Place the last element at the first position
            userInputArray[0] = lastElementValue;
        } else {
            // If the array size is 0 or 1, the array remains the same after the shift
            // We still keep this branch for clarity and safety
            int doNothingHolder = arraySize;
            if (doNothingHolder != -1) {
                // This does nothing but keeps the structure explicit
            }
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