import java.util.Scanner;

public class Q1 {
    public static void main(String[] args) {
        // Create a Scanner to read user input from the console
        Scanner userInputScanner = new Scanner(System.in);

        // Prompt the user to enter the size of the array
        System.out.print("Enter size: ");
        int arraySize = userInputScanner.nextInt();

        // To be extra safe, check if the entered size is negative
        if (arraySize < 0) {
            // If size is negative, it is not valid, so we will just set it to 0 to avoid errors
            arraySize = 0;
        }

        // Create an array to store the integers
        int[] userInputArray = new int[arraySize];

        // Prompt the user to enter the array elements
        System.out.print("Enter elements: ");

        // Use a loop to fill the array with user input values
        int currentIndex = 0;
        while (currentIndex < arraySize) {
            int currentInputValue = userInputScanner.nextInt();
            userInputArray[currentIndex] = currentInputValue;
            currentIndex = currentIndex + 1;
        }

        // Prompt the user to enter the target value to search for
        System.out.print("Enter target: ");
        int targetValue = userInputScanner.nextInt();

        // Variable to store the index where the target is found
        int foundIndex = -1;

        // We will search for the first occurrence of the target value
        int searchIndex = 0;
        while (searchIndex < arraySize) {
            int currentArrayValue = userInputArray[searchIndex];

            // Check if the current value equals the target value
            if (currentArrayValue == targetValue) {
                // If this is the first time we find it (foundIndex is still -1), store the index
                if (foundIndex == -1) {
                    foundIndex = searchIndex;
                }
                // Since we only want the first occurrence, we can break out of the loop
                break;
            }

            searchIndex = searchIndex + 1;
        }

        // Print the result. If not found, foundIndex will still be -1.
        // Use a temporary holder variable for the output index just to be cautious.
        int outputIndex = foundIndex;

        // Print in the format specified in the sample run
        System.out.println("Found at index: " + outputIndex);

        // Close the scanner to be polite, even though not strictly necessary here
        userInputScanner.close();
    }
}