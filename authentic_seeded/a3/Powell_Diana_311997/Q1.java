import java.util.Scanner;

public class Q1 {
    public static void main(String[] args) {
        // Create a Scanner to read user input from the console
        Scanner userInputScanner = new Scanner(System.in);

        // Ask the user for the size of the array
        System.out.print("Enter size: ");
        int arraySize = userInputScanner.nextInt();

        // Create an array to store the integers
        int[] userInputArray = new int[arraySize];

        // Ask the user to enter the elements of the array
        System.out.print("Enter elements: ");
        int currentIndex = 0;
        while (currentIndex < arraySize) {
            // Read each element and store it in the array
            int userInputValue = userInputScanner.nextInt();
            userInputArray[currentIndex] = userInputValue;
            currentIndex = currentIndex + 1;
        }

        // Ask the user to enter the target value
        System.out.print("Enter target: ");
        int targetValue = userInputScanner.nextInt();

        // Variable to store the index where the target is first found
        int firstOccurrenceIndex = -1;

        // Loop through the array to find the first occurrence of the target
        int searchIndex = 0;
        while (searchIndex < arraySize) {
            int currentArrayValue = userInputArray[searchIndex];

            // Check if the current array value equals the target value
            if (currentArrayValue == targetValue) {
                // If this is the first time we find the target, store the index
                if (firstOccurrenceIndex == -1) {
                    firstOccurrenceIndex = searchIndex;
                }
                // Since we only need the first occurrence, we can break
                break;
            }

            searchIndex = searchIndex + 1;
        }

        // Print the result with the required format
        // If the target was not found, firstOccurrenceIndex will still be -1
        System.out.println("Found at index: " + firstOccurrenceIndex);

        // Close the scanner to be safe
        userInputScanner.close();
    }
}