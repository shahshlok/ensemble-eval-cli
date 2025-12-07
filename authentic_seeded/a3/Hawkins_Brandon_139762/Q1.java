import java.util.Scanner;

public class Q1 {
    public static void main(String[] args) {
        // Create scanner to read user input from the keyboard
        Scanner userInputScanner = new Scanner(System.in);

        // Step 1: Ask the user for the size of the array
        System.out.print("Enter size: ");
        int arraySize = userInputScanner.nextInt();

        // Step 2: Create an array with the given size
        int[] userNumberArray = new int[arraySize];

        // Step 3: Ask the user to enter the array elements
        System.out.print("Enter elements: ");
        for (int currentIndex = 0; currentIndex < arraySize; currentIndex++) {
            // Read each integer and store it in the array
            userNumberArray[currentIndex] = userInputScanner.nextInt();
        }

        // Step 4: Ask the user for the target number to search for
        System.out.print("Enter target: ");
        int targetValue = userInputScanner.nextInt();

        // Step 5: Initialize the found index to -1 (meaning not found yet)
        int firstOccurrenceIndex = -1;

        // Step 6: Loop through the array to find the first occurrence of the target
        for (int currentIndex = 0; currentIndex < arraySize; currentIndex++) {
            // Check if the current element matches the target
            if (userNumberArray[currentIndex] == targetValue) {
                // If we find the target, store the index
                firstOccurrenceIndex = currentIndex;
                // Since we only want the first occurrence, break out of the loop
                break;
            }
        }

        // Step 7: Print the result, either the index or -1 if not found
        System.out.println("Found at index: " + firstOccurrenceIndex);

        // Step 8: Close the scanner
        userInputScanner.close();
    }
}