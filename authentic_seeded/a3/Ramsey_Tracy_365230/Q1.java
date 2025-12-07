import java.util.Scanner;

public class Q1 {
    public static void main(String[] args) {
        // Create a Scanner object so we can read user input from the keyboard
        Scanner keyboardInput = new Scanner(System.in);

        // Step 1: Ask the user for the size of the array
        System.out.print("Enter size: ");
        int arraySize = keyboardInput.nextInt();

        // Step 2: Create an integer array with the given size
        int[] userNumberArray = new int[arraySize];

        // Step 3: Ask the user to enter the elements of the array
        System.out.print("Enter elements: ");
        for (int currentIndex = 0; currentIndex < arraySize; currentIndex++) {
            // Read each integer and store it in the array
            userNumberArray[currentIndex] = keyboardInput.nextInt();
        }

        // Step 4: Ask the user for the target number to search for
        System.out.print("Enter target: ");
        int targetNumber = keyboardInput.nextInt();

        // Step 5: Initialize a variable to store the index of the first occurrence
        // Start with -1 to mean "not found yet"
        int firstOccurrenceIndex = -1;

        // Step 6: Loop through the array to find the first occurrence of the target
        for (int currentIndex = 0; currentIndex < arraySize; currentIndex++) {
            // Check if the current element matches the target number
            if (userNumberArray[currentIndex] == targetNumber) {
                // If it matches, store the index
                firstOccurrenceIndex = currentIndex;
                // Break out of the loop because we only want the first occurrence
                break;
            }
        }

        // Step 7: Print the result in the required format
        System.out.println("Found at index: " + firstOccurrenceIndex);

        // Step 8: Close the scanner because we are done with input
        keyboardInput.close();
    }
}