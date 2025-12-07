import java.util.Scanner;

public class Q1 {
    public static void main(String[] args) {
        // Create a Scanner so we can read input from the user
        Scanner userInputScanner = new Scanner(System.in);

        // Step 1: Ask the user for the size of the array
        System.out.print("Enter size: ");
        int arraySize = userInputScanner.nextInt();

        // Step 2: Create an array to store the integers
        int[] userNumbersArray = new int[arraySize];

        // Step 3: Ask the user to enter the elements of the array
        System.out.print("Enter elements: ");
        for (int arrayIndex = 0; arrayIndex < arraySize; arrayIndex++) {
            // Read each integer and store it in the array
            userNumbersArray[arrayIndex] = userInputScanner.nextInt();
        }

        // Step 4: Ask the user for the target value to search for
        System.out.print("Enter target: ");
        int targetValue = userInputScanner.nextInt();

        // Step 5: Search for the first occurrence of the target value
        int firstOccurrenceIndex = -1; // Start with -1, meaning "not found"
        for (int arrayIndex = 0; arrayIndex < arraySize; arrayIndex++) {
            // Check if the current element matches the target
            if (userNumbersArray[arrayIndex] == targetValue) {
                // If we find it, store the index and break out of the loop
                firstOccurrenceIndex = arrayIndex;
                break;
            }
        }

        // Step 6: Print the result in the required format
        System.out.println("Found at index: " + firstOccurrenceIndex);

        // Close the scanner at the end
        userInputScanner.close();
    }
}