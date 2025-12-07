import java.util.Scanner;

public class Q1 {
    public static void main(String[] args) {
        // Step 1: Create a Scanner to read input from the user
        Scanner userInputScanner = new Scanner(System.in);

        // Step 2: Ask the user to enter the size of the array
        System.out.print("Enter size: ");
        int arraySize = userInputScanner.nextInt();

        // Step 3: Create an array with the given size
        int[] userNumberArray = new int[arraySize];

        // Step 4: Ask the user to enter the elements of the array
        System.out.print("Enter elements: ");
        for (int arrayIndex = 0; arrayIndex < arraySize; arrayIndex++) {
            // Read each integer and store it in the array
            userNumberArray[arrayIndex] = userInputScanner.nextInt();
        }

        // Step 5: Ask the user to enter the target value to search for
        System.out.print("Enter target: ");
        int targetValue = userInputScanner.nextInt();

        // Step 6: Initialize a variable to store the found index, default to -1 (not found)
        int foundIndex = -1;

        // Step 7: Loop through the array to find the first occurrence of the target
        for (int arrayIndex = 0; arrayIndex < arraySize; arrayIndex++) {
            // If the current element matches the target and we have not recorded an index yet
            if (userNumberArray[arrayIndex] == targetValue) {
                foundIndex = arrayIndex; // Store the index
                break; // Stop the loop because we only want the first occurrence
            }
        }

        // Step 8: Print the result: either the found index or -1 if not found
        System.out.println("Found at index: " + foundIndex);

        // Step 9: Close the scanner
        userInputScanner.close();
    }
}