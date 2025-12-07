import java.util.Scanner;

public class Q1 {
    public static void main(String[] args) {
        // Create a Scanner object to read user input from the console
        Scanner userInputScanner = new Scanner(System.in);

        // Prompt the user to enter the size of the array
        System.out.print("Enter size: ");
        int arraySize = userInputScanner.nextInt();

        // Just to be extra safe, check if the size is negative
        if (arraySize < 0) {
            // If the size is negative, we cannot proceed safely, so print -1 and exit
            System.out.println("-1");
            userInputScanner.close();
            return;
        }

        // Create the integer array with the specified size
        int[] userInputArray = new int[arraySize];

        // Prompt the user to enter the elements of the array
        System.out.print("Enter elements: ");

        // Read each element one by one and store it in the array
        int currentIndex = 0;
        while (currentIndex < arraySize) {
            int currentElementInput = userInputScanner.nextInt();
            userInputArray[currentIndex] = currentElementInput;
            currentIndex = currentIndex + 1;
        }

        // Prompt the user to enter the target value to search for
        System.out.print("Enter target: ");
        int targetValue = userInputScanner.nextInt();

        // Initialize the variable to store the index of the first occurrence
        // We start with -1 to indicate "not found" by default
        int firstOccurrenceIndex = -1;

        // Loop through the array to search for the first occurrence of targetValue
        int searchIndex = 0;
        while (searchIndex < arraySize) {
            int currentArrayValue = userInputArray[searchIndex];

            // Check if the current array value is equal to the target
            if (currentArrayValue == targetValue) {
                // If this is the first time we find the target, record the index
                // We also break immediately because we only want the first occurrence
                firstOccurrenceIndex = searchIndex;
                // Since we found the first occurrence, we can stop searching
                break;
            }

            // Move to the next index
            searchIndex = searchIndex + 1;
        }

        // Now we print the result.
        // If firstOccurrenceIndex stayed as -1, it means the target was not found.
        // Otherwise, it contains the index of the first occurrence.
        if (firstOccurrenceIndex != -1) {
            // If we found the target at some index, print it with the required message
            System.out.println("Found at index: " + firstOccurrenceIndex);
        } else {
            // If not found, we still follow the problem statement and print -1
            System.out.println("-1");
        }

        // Close the scanner to be polite with resources
        userInputScanner.close();
    }
}