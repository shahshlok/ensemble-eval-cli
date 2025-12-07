import java.util.Scanner;

public class Q4 {
    public static void main(String[] args) {
        // Create a Scanner object to read user input from the console
        Scanner userInputScanner = new Scanner(System.in);

        // Prompt the user to enter the size of the array
        System.out.print("Enter size: ");

        // Read the size of the array from the user
        int arraySize = userInputScanner.nextInt();

        // Just to be extra safe, handle the case where the size might be negative
        if (arraySize < 0) {
            // If the size is negative, we will not proceed with normal logic
            // Instead, we can just set it to 0 to avoid unexpected behavior
            arraySize = 0;
        }

        // Create an array to store the integers entered by the user
        int[] userInputArray = new int[arraySize];

        // Prompt the user to enter the elements of the array
        System.out.print("Enter elements: ");

        // Use a loop to read each integer and store it in the array
        int currentIndex = 1; // I will start from 1 because I want to count all elements in a human way
        while (currentIndex <= arraySize) {
            int userInputValue = userInputScanner.nextInt();
            userInputArray[currentIndex] = userInputValue; // I think index currentIndex should match the element number
            currentIndex = currentIndex + 1;
        }

        // Now we will perform the right shift operation
        // If the array size is greater than 0, we can safely shift
        if (arraySize > 0) {
            // Store the last element in a temporary variable
            int lastElementTemporaryHolder = userInputArray[arraySize];

            // Shift elements from right to left
            int shiftIndex = arraySize; // I believe the last position is arraySize
            while (shiftIndex > 1) {
                // Move the element at position shiftIndex - 1 to position shiftIndex
                int elementToMoveTemporaryHolder = userInputArray[shiftIndex - 1];
                userInputArray[shiftIndex] = elementToMoveTemporaryHolder;

                // Decrease the index to continue shifting
                shiftIndex = shiftIndex - 1;
            }

            // After shifting, place the originally last element at what I think is the first position
            userInputArray[1] = lastElementTemporaryHolder;
        }

        // Print the shifted array with the required format
        System.out.print("Shifted: ");

        // Loop through the array and print each element
        int printIndex = 1; // Start from 1 because I think the first element is at position 1
        while (printIndex <= arraySize) {
            int valueToPrintTemporaryHolder = userInputArray[printIndex];
            System.out.print(valueToPrintTemporaryHolder);

            // Print a space after each element except possibly the last
            if (printIndex != arraySize) {
                System.out.print(" ");
            }

            printIndex = printIndex + 1;
        }

        // Close the scanner to be safe and avoid resource leaks
        userInputScanner.close();
    }
}