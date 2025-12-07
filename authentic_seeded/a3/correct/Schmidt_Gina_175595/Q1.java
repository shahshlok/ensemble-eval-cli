import java.util.Scanner;

public class Q1 {
    public static void main(String[] args) {
        // Create a Scanner object so we can read input from the user
        Scanner userInputScanner = new Scanner(System.in);

        // Ask the user for the size of the array
        System.out.print("Enter size: ");
        int arraySize = userInputScanner.nextInt();

        // Create an array with the given size
        int[] userNumberArray = new int[arraySize];

        // Ask the user to enter the elements of the array
        System.out.print("Enter elements: ");
        for (int arrayIndex = 0; arrayIndex < arraySize; arrayIndex++) {
            // Read each element and store it in the array
            userNumberArray[arrayIndex] = userInputScanner.nextInt();
        }

        // Ask the user for the target value to search for
        System.out.print("Enter target: ");
        int targetValue = userInputScanner.nextInt();

        // Initialize the variable to store the index of the first occurrence
        // We start with -1 to indicate "not found" by default
        int firstOccurrenceIndex = -1;

        // Loop through the array to find the first occurrence of the target value
        for (int arrayIndex = 0; arrayIndex < arraySize; arrayIndex++) {
            // Check if the current element matches the target value
            if (userNumberArray[arrayIndex] == targetValue) {
                // If we find the target, store the index
                firstOccurrenceIndex = arrayIndex;
                // Break out of the loop because we only want the first occurrence
                break;
            }
        }

        // Print the result with the required message format
        System.out.println("Found at index: " + firstOccurrenceIndex);

        // Close the scanner to free system resources
        userInputScanner.close();
    }
}