import java.util.Scanner;

public class Q1 {
    public static void main(String[] args) {
        // Step 1: Create a Scanner to read input from the user
        Scanner userInputScanner = new Scanner(System.in);

        // Step 2: Ask the user for the size of the array and read it
        System.out.print("Enter size: ");
        int arraySize = userInputScanner.nextInt();

        // Step 3: Create an array with the given size
        int[] userNumberArray = new int[arraySize];

        // Step 4: Ask the user to enter the elements of the array
        System.out.print("Enter elements: ");
        for (int arrayIndex = 0; arrayIndex < arraySize; arrayIndex++) {
            // Read each element and store it in the array
            userNumberArray[arrayIndex] = userInputScanner.nextInt();
        }

        // Step 5: Ask the user for the target number and read it
        System.out.print("Enter target: ");
        int targetValue = userInputScanner.nextInt();

        // Step 6: Set a variable to store the first index where the target is found
        // We start with -1 to mean "not found yet"
        int firstOccurrenceIndex = -1;

        // Step 7: Go through the array from the beginning
        for (int arrayIndex = 0; arrayIndex < arraySize; arrayIndex++) {
            // Step 8: Check if the current element is equal to the target
            if (userNumberArray[arrayIndex] == targetValue) {
                // Step 9: Store the index where we found the target
                firstOccurrenceIndex = arrayIndex;
                // Step 10: Break out of the loop because we only want the first occurrence
                break;
            }
        }

        // Step 11: Print the result
        System.out.println("Found at index: " + firstOccurrenceIndex);

        // Step 12: Close the scanner
        userInputScanner.close();
    }
}