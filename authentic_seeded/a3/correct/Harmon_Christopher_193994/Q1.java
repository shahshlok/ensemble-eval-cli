import java.util.Scanner;

public class Q1 {
    public static void main(String[] args) {
        // Create a Scanner to read user input from standard input
        Scanner userInputScanner = new Scanner(System.in);

        // Prompt the user to enter the size of the array
        System.out.print("Enter size: ");
        int arraySize = userInputScanner.nextInt();

        // Just to be extra careful, handle negative sizes by treating them as zero
        if (arraySize < 0) {
            arraySize = 0;
        }

        // Create the array with the given size
        int[] userInputArray = new int[arraySize];

        // Prompt the user to enter the elements of the array
        System.out.print("Enter elements: ");
        int currentIndex = 0;
        while (currentIndex < arraySize) {
            int currentInputValue = userInputScanner.nextInt();
            userInputArray[currentIndex] = currentInputValue;
            currentIndex = currentIndex + 1;
        }

        // Prompt the user to enter the target value to search for
        System.out.print("Enter target: ");
        int targetValue = userInputScanner.nextInt();

        // Variable to hold the index of the first occurrence of the target
        int firstOccurrenceIndex = -1;

        // Loop through the array to search for the target
        int searchIndex = 0;
        while (searchIndex < arraySize) {
            int currentArrayValue = userInputArray[searchIndex];

            // Check if the current array element matches the target
            if (currentArrayValue == targetValue) {
                // If we find the target, store the index and break out of the loop
                firstOccurrenceIndex = searchIndex;

                // Since we only want the first occurrence, we stop searching
                break;
            }

            // Move to the next index
            searchIndex = searchIndex + 1;
        }

        // Print the result
        if (firstOccurrenceIndex != -1) {
            System.out.println("Found at index: " + firstOccurrenceIndex);
        } else {
            // If the target was never found, print -1
            System.out.println("-1");
        }

        // Close the scanner to avoid resource leaks, even though the program is ending
        userInputScanner.close();
    }
}