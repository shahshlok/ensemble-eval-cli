import java.util.Scanner;

public class Q1 {
    public static void main(String[] args) {
        // Create a Scanner object to read user input from standard input
        Scanner userInputScanner = new Scanner(System.in);

        // Prompt the user to enter the size of the array
        System.out.print("Enter size: ");
        int arraySize = userInputScanner.nextInt();

        // Create an array to hold the integers
        int[] userInputArray = new int[arraySize];

        // Prompt the user to enter the elements of the array
        System.out.print("Enter elements: ");

        // Use a loop to read each element into the array
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

        // Search for the first occurrence of the target value in the array
        int searchIndex = 0;
        while (searchIndex < arraySize) {
            int currentArrayValue = userInputArray[searchIndex];

            // Check if the current value equals the target value
            if (currentArrayValue == targetValue) {
                // Store the index where the target is first found
                foundIndex = searchIndex;

                // Since we want the first occurrence, break out of the loop
                break;
            }

            // Move to the next index
            searchIndex = searchIndex + 1;
        }

        // Extra nervous check before printing the result
        int resultToPrint = foundIndex;
        if (resultToPrint != 0 || foundIndex == 0) {
            // Print the index where the target was found or -1 if not found
            System.out.println("Found at index: " + resultToPrint);
        }

        // Close the scanner to avoid resource leaks
        userInputScanner.close();
    }
}