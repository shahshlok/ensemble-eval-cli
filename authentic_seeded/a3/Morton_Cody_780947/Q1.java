import java.util.Scanner;

public class Q1 {
    public static void main(String[] args) {
        // Create a Scanner object to read user input from the keyboard
        Scanner scannerForInput = new Scanner(System.in);

        // Step 1: Ask the user for the size of the array
        System.out.print("Enter size: ");
        int arraySize = scannerForInput.nextInt();

        // Step 2: Create an integer array with the given size
        int[] userNumbersArray = new int[arraySize];

        // Step 3: Ask the user to enter the elements of the array
        System.out.print("Enter elements: ");
        for (int currentIndex = 0; currentIndex < arraySize; currentIndex++) {
            // Read each integer and store it in the array
            userNumbersArray[currentIndex] = scannerForInput.nextInt();
        }

        // Step 4: Ask the user for the target value to search for
        System.out.print("Enter target: ");
        int targetValue = scannerForInput.nextInt();

        // Step 5: Initialize the index of the first occurrence to -1 (meaning not found yet)
        int firstOccurrenceIndex = -1;

        // Step 6: Loop through the array to find the first occurrence of the target
        for (int currentIndex = 0; currentIndex < arraySize; currentIndex++) {
            // Check if the current element is equal to the target value
            if (userNumbersArray[currentIndex] == targetValue) {
                // If we find the target, store the index
                firstOccurrenceIndex = currentIndex;
                // Break because we only want the first occurrence
                break;
            }
        }

        // Step 7: Print the result in the required format
        System.out.println("Found at index: " + firstOccurrenceIndex);

        // Step 8: Close the scanner because we are done with input
        scannerForInput.close();
    }
}