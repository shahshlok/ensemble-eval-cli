import java.util.Scanner;

public class Q1 {
    public static void main(String[] args) {
        // Create a Scanner to read user input from the keyboard
        Scanner keyboardScanner = new Scanner(System.in);

        // Ask the user for the size of the array
        System.out.print("Enter size: ");
        int arraySize = keyboardScanner.nextInt();

        // Create an array to store the integers
        int[] userInputArray = new int[arraySize];

        // Ask the user to enter the elements of the array
        System.out.print("Enter elements: ");
        for (int currentIndex = 0; currentIndex < arraySize; currentIndex++) {
            // Read each integer and store it in the array
            userInputArray[currentIndex] = keyboardScanner.nextInt();
        }

        // Ask the user for the target value to search for
        System.out.print("Enter target: ");
        int targetValue = keyboardScanner.nextInt();

        // Initialize the variable to store the index of the first occurrence
        // Start with -1 to indicate "not found" by default
        int firstOccurrenceIndex = -1;

        // Go through the array from the beginning
        for (int currentIndex = 0; currentIndex < arraySize; currentIndex++) {
            // Check if the current element matches the target
            if (userInputArray[currentIndex] == targetValue) {
                // Store the index where we found the first occurrence
                firstOccurrenceIndex = currentIndex;
                // Break out of the loop because we only need the first occurrence
                break;
            }
        }

        // Print the result in the required format
        System.out.println("Found at index: " + firstOccurrenceIndex);

        // Close the scanner
        keyboardScanner.close();
    }
}