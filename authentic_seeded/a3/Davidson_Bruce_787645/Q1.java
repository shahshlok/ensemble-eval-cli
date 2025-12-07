import java.util.Scanner;

public class Q1 {
    public static void main(String[] args) {
        // Create a Scanner object to read input from the user
        Scanner userInputScanner = new Scanner(System.in);

        // Prompt the user to enter the size of the array
        System.out.print("Enter size: ");
        int arraySizeInput = userInputScanner.nextInt();

        // Create an array to hold the integers
        int[] userInputArray = new int[arraySizeInput];

        // Prompt the user to enter the elements
        System.out.print("Enter elements: ");

        // Read each element into the array
        int currentIndex = 0;
        while (currentIndex < arraySizeInput) {
            int currentInputValue = userInputScanner.nextInt();
            userInputArray[currentIndex] = currentInputValue;
            currentIndex = currentIndex + 1;
        }

        // Prompt the user to enter the target value
        System.out.print("Enter target: ");
        int targetValueToFind = userInputScanner.nextInt();

        // Variable to store the index of the first occurrence
        int firstOccurrenceIndex = -1;

        // Search for the first occurrence of the target in the array
        int searchIndex = 0;
        while (searchIndex < arraySizeInput) {
            int currentArrayValue = userInputArray[searchIndex];

            // Check if the current value is equal to the target
            if (currentArrayValue == targetValueToFind) {
                // If firstOccurrenceIndex is still -1, we have not found it before
                if (firstOccurrenceIndex == -1) {
                    firstOccurrenceIndex = searchIndex;
                }
                // Since we only want the first occurrence, we break out of the loop
                break;
            }

            searchIndex = searchIndex + 1;
        }

        // Print the result with the required format
        System.out.print("Found at index: ");
        System.out.println(firstOccurrenceIndex);

        // Close the scanner to be safe
        userInputScanner.close();
    }
}