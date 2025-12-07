import java.util.Scanner;

public class Q1 {
    public static void main(String[] args) {
        // Create a Scanner object to read user input from the console
        Scanner userInputScanner = new Scanner(System.in);

        // Prompt the user to enter the size of the array
        System.out.print("Enter size: ");
        int arraySize = userInputScanner.nextInt();

        // Extra nervous check: make sure the array size is not negative
        if (arraySize < 0) {
            arraySize = 0;
        }

        // Create the array with the given size
        int[] userInputArray = new int[arraySize];

        // Prompt the user to enter the elements of the array
        System.out.print("Enter elements: ");

        // Use a loop to read each element into the array
        int currentIndex = 0;
        while (currentIndex < arraySize) {
            int currentElementValue = userInputScanner.nextInt();
            userInputArray[currentIndex] = currentElementValue;
            currentIndex = currentIndex + 1;
        }

        // Prompt the user to enter the target value to search for
        System.out.print("Enter target: ");
        int targetValue = userInputScanner.nextInt();

        // Variable to store the index of the first occurrence of the target
        int firstOccurrenceIndex = -1;

        // Loop through the array to find the first occurrence of the target
        int searchIndex = 0;
        while (searchIndex < arraySize) {
            int currentArrayValue = userInputArray[searchIndex];

            // Check if the current array value matches the target
            if (currentArrayValue == targetValue) {
                // If we find the target and firstOccurrenceIndex is still -1, we store the index
                if (firstOccurrenceIndex == -1) {
                    firstOccurrenceIndex = searchIndex;
                }
                // Break early since we only want the first occurrence
                break;
            }

            searchIndex = searchIndex + 1;
        }

        // Print the result: either the index of the first occurrence or -1 if not found
        System.out.println("Found at index: " + firstOccurrenceIndex);

        // Close the scanner to be safe
        userInputScanner.close();
    }
}