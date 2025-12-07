import java.util.Scanner;

public class Q1 {
    public static void main(String[] args) {
        // Create a Scanner object to read user input from standard input (keyboard)
        Scanner userInputScanner = new Scanner(System.in);

        // Prompt the user to enter the size of the array
        System.out.print("Enter size: ");

        // Read the size of the array
        int arraySize = userInputScanner.nextInt();

        // Extra safety check: make sure array size is not negative
        if (arraySize < 0) {
            // If the array size is negative, we print -1 because no valid search can be done
            System.out.println("-1");
            // Exit the program early because we cannot proceed
            return;
        }

        // Create an array to hold the integers, using the provided size
        int[] userInputArray = new int[arraySize];

        // Prompt the user to enter the elements of the array
        System.out.print("Enter elements: ");

        // Read each element from the user and store it in the array
        int currentIndex = 0;
        while (currentIndex < arraySize) {
            int currentInputValue = userInputScanner.nextInt();
            userInputArray[currentIndex] = currentInputValue;
            currentIndex = currentIndex + 1;
        }

        // Prompt the user to enter the target number to search for
        System.out.print("Enter target: ");

        // Read the target value
        int targetValue = userInputScanner.nextInt();

        // Initialize the variable that will hold the index of the first occurrence
        // We start with -1 to mean "not found yet"
        int firstOccurrenceIndex = -1;

        // We will search the array from the beginning to the end
        int searchIndex = 0;
        while (searchIndex < arraySize) {
            // Get the value at the current position in the array
            int currentArrayValue = userInputArray[searchIndex];

            // Check if the current value equals the target value
            if (currentArrayValue == targetValue) {
                // If we find the target for the first time, store this index
                firstOccurrenceIndex = searchIndex;

                // Since we only want the first occurrence, we break out of the loop
                break;
            }

            // Move to the next index
            searchIndex = searchIndex + 1;
        }

        // Extra check before printing, even though it is not strictly needed
        if (firstOccurrenceIndex != -1) {
            // If we found the target, print the index in the required format
            System.out.println("Found at index: " + firstOccurrenceIndex);
        } else {
            // If we never found the target, print -1
            System.out.println("-1");
        }

        // Close the scanner to be polite, even though the program is ending
        userInputScanner.close();
    }
}