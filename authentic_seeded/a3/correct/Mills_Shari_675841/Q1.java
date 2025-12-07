import java.util.Scanner;

public class Q1 {
    public static void main(String[] args) {
        // Create a Scanner object to read user input from the keyboard
        Scanner keyboardScanner = new Scanner(System.in);

        // Step 1: Ask the user for the size of the array
        System.out.print("Enter size: ");
        int arraySize = keyboardScanner.nextInt();

        // Step 2: Create an array with the given size
        int[] userNumbersArray = new int[arraySize];

        // Step 3: Ask the user to enter the elements of the array
        System.out.print("Enter elements: ");
        for (int currentIndex = 0; currentIndex < arraySize; currentIndex++) {
            // Read each integer and store it in the array
            userNumbersArray[currentIndex] = keyboardScanner.nextInt();
        }

        // Step 4: Ask the user for the target number to search for
        System.out.print("Enter target: ");
        int targetNumber = keyboardScanner.nextInt();

        // Step 5: Initialize a variable to store the index where we find the target
        // We start with -1 to mean "not found yet"
        int firstOccurrenceIndex = -1;

        // Step 6: Loop through the array to find the first occurrence of the target
        for (int currentIndex = 0; currentIndex < arraySize; currentIndex++) {
            // Check if the current element is equal to the target number
            if (userNumbersArray[currentIndex] == targetNumber) {
                // If we find it, store the index
                firstOccurrenceIndex = currentIndex;
                // Break the loop because we only want the first occurrence
                break;
            }
        }

        // Step 7: Print the result in the required format
        System.out.println("Found at index: " + firstOccurrenceIndex);

        // Step 8: Close the scanner to avoid resource leaks
        keyboardScanner.close();
    }
}