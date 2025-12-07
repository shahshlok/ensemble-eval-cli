import java.util.Scanner;

public class Q1 {
    public static void main(String[] args) {
        // Create a Scanner object so we can read user input from the keyboard
        Scanner userInputScanner = new Scanner(System.in);

        // Step 1: Ask the user for the size of the array
        System.out.print("Enter size: ");
        int arraySize = userInputScanner.nextInt();

        // Step 2: Create an array with the given size
        int[] numberArray = new int[arraySize];

        // Step 3: Ask the user to enter the elements of the array
        System.out.print("Enter elements: ");
        for (int currentIndex = 0; currentIndex < arraySize; currentIndex++) {
            // Read each integer and store it in the array
            numberArray[currentIndex] = userInputScanner.nextInt();
        }

        // Step 4: Ask the user for the target value to search for
        System.out.print("Enter target: ");
        int targetValue = userInputScanner.nextInt();

        // Step 5: Search for the first occurrence of the target value
        int foundIndex = -1; // Start with -1 to mean "not found yet"

        // Go through each element in the array from start to end
        for (int currentIndex = 0; currentIndex < arraySize; currentIndex++) {
            // If the current element equals the target and we have not set foundIndex yet
            if (numberArray[currentIndex] == targetValue) {
                foundIndex = currentIndex; // Remember this index
                break; // Stop the loop because we found the first occurrence
            }
        }

        // Step 6: Print the result
        System.out.println("Found at index: " + foundIndex);

        // Close the scanner because we are done with input
        userInputScanner.close();
    }
}