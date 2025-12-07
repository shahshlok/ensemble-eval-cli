import java.util.Scanner;

public class Q1 {
    public static void main(String[] args) {
        // Create a Scanner object to read user input from the keyboard
        Scanner userInputScanner = new Scanner(System.in);

        // Prompt the user to enter the size of the array
        System.out.print("Enter size: ");
        int arraySizeN = userInputScanner.nextInt();

        // Create an integer array with the given size
        int[] userInputArray = new int[arraySizeN];

        // Prompt the user to enter the elements of the array
        System.out.print("Enter elements: ");
        for (int currentIndex = 0; currentIndex < arraySizeN; currentIndex++) {
            // Read each element and store it in the array
            userInputArray[currentIndex] = userInputScanner.nextInt();
        }

        // Prompt the user to enter the target value T
        System.out.print("Enter target: ");
        int targetValueT = userInputScanner.nextInt();

        // Initialize the result index to -1 assuming the target is not found at first
        int firstOccurrenceIndex = -1;

        // Initialize some intermediate math-like variables for clarity
        int loopIndexI = 0;
        int currentArrayValue = 0;

        // Loop through the array to find the first occurrence of the target
        for (loopIndexI = 0; loopIndexI < arraySizeN; loopIndexI++) {
            // Set the current array value
            currentArrayValue = userInputArray[loopIndexI];

            // Check if the current array value is equal to the target
            if (currentArrayValue == targetValueT) {
                // If equal, set the firstOccurrenceIndex to the current index
                firstOccurrenceIndex = loopIndexI;
                // Break the loop because we only want the first occurrence
                break;
            }
        }

        // Print the result index of the first occurrence (or -1 if not found)
        System.out.println("Found at index: " + firstOccurrenceIndex);

        // Close the scanner to free resources
        userInputScanner.close();
    }
}