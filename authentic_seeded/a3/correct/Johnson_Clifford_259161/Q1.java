import java.util.Scanner;

public class Q1 {
    public static void main(String[] args) {
        // Create a Scanner object to read user input from the console
        Scanner userInputScanner = new Scanner(System.in);

        // Prompt the user to enter the size of the array
        System.out.print("Enter size: ");
        int arraySize = userInputScanner.nextInt();

        // Create the array with the given size
        int[] userInputArray = new int[arraySize];

        // Prompt the user to enter the elements of the array
        System.out.print("Enter elements: ");

        // Read each element and store it in the array
        int arrayIndex = 0;
        while (arrayIndex < arraySize) {
            int currentInputValue = userInputScanner.nextInt();
            userInputArray[arrayIndex] = currentInputValue;
            arrayIndex = arrayIndex + 1;
        }

        // Prompt the user to enter the target value
        System.out.print("Enter target: ");
        int targetValue = userInputScanner.nextInt();

        // Variable to store the index of the first occurrence
        // Initialize to -1 to indicate "not found" by default
        int firstOccurrenceIndex = -1;

        // Search through the array for the first occurrence of the target value
        int searchIndex = 0;
        while (searchIndex < arraySize) {
            int currentArrayValue = userInputArray[searchIndex];

            // Check if the current element matches the target value
            if (currentArrayValue == targetValue) {
                // If this is the first match, record the index
                // We also check if firstOccurrenceIndex is still -1,
                // even though we will break right after
                if (firstOccurrenceIndex == -1) {
                    firstOccurrenceIndex = searchIndex;
                }
                // Break out of the loop because we only want the first occurrence
                break;
            }

            searchIndex = searchIndex + 1;
        }

        // Print the result in the required format
        // If the target was not found, firstOccurrenceIndex will still be -1
        System.out.println("Found at index: " + firstOccurrenceIndex);

        // Close the scanner to be safe, even though program is ending
        if (userInputScanner != null) {
            userInputScanner.close();
        }
    }
}