import java.util.Scanner;

public class Q1 {
    public static void main(String[] args) {
        // Create a Scanner to read user input from standard input (keyboard)
        Scanner keyboardInputScanner = new Scanner(System.in);

        // Prompt the user to enter the size of the array
        System.out.print("Enter size: ");
        int arraySize = keyboardInputScanner.nextInt();

        // Just to be extra safe, check if the size is negative
        if (arraySize < 0) {
            // If the size is negative, we cannot create an array properly, so print -1 and stop
            System.out.println("-1");
            keyboardInputScanner.close();
            return;
        }

        // Create an integer array to store the elements
        int[] userInputArray = new int[arraySize];

        // Prompt the user to enter the elements of the array
        System.out.print("Enter elements: ");

        // Read each integer and store it in the array
        int currentIndex = 0;
        while (currentIndex < arraySize) {
            int currentInputValue = keyboardInputScanner.nextInt();
            userInputArray[currentIndex] = currentInputValue;
            currentIndex = currentIndex + 1;
        }

        // Prompt the user to enter the target value to search for
        System.out.print("Enter target: ");
        int targetValue = keyboardInputScanner.nextInt();

        // Variable to hold the index of the first occurrence, start with -1 as "not found"
        int firstOccurrenceIndex = -1;

        // Search through the array to find the first occurrence of the target
        int searchIndex = 0;
        while (searchIndex < arraySize) {
            int currentArrayValue = userInputArray[searchIndex];

            // Check if the current value matches the target value
            if (currentArrayValue == targetValue) {
                // If this is the first occurrence, store the index and break out
                firstOccurrenceIndex = searchIndex;
                // We found the first occurrence, so we can stop searching
                break;
            }

            searchIndex = searchIndex + 1;
        }

        // Now print the result. If the value was never found, firstOccurrenceIndex will still be -1.
        // Since the question's example uses a label, we will match that format.
        if (firstOccurrenceIndex != -1) {
            System.out.println("Found at index: " + firstOccurrenceIndex);
        } else {
            // If not found, print -1 directly as an integer, without the label,
            // but to be extra safe with formatting, we could also keep the style consistent.
            System.out.println("-1");
        }

        // Close the scanner to be polite to system resources
        keyboardInputScanner.close();
    }
}