import java.util.Scanner;

public class Q4 {
    public static void main(String[] args) {
        // Create a Scanner object to read input from the user
        Scanner userInputScanner = new Scanner(System.in);

        // Prompt the user to enter the size of the array
        System.out.print("Enter size: ");
        int arraySize = userInputScanner.nextInt();

        // Create an array to store the integers
        int[] userInputArray = new int[arraySize];

        // Prompt the user to enter the elements
        System.out.print("Enter elements: ");

        // Read the elements into the array
        for (int arrayIndex = 0; arrayIndex < arraySize; arrayIndex++) {
            userInputArray[arrayIndex] = userInputScanner.nextInt();
        }

        // If the array has at least one element, we perform the right shift
        if (arraySize > 0) {
            // Store the last element because it will wrap around to the front
            int lastElementValue = userInputArray[arraySize - 1];

            // We will shift elements one position to the right
            // We do this from the end towards the beginning
            for (int arrayIndex = arraySize - 1; arrayIndex > 0; arrayIndex--) {
                int a = arrayIndex;                  // current index position
                int b = arrayIndex - 1;              // previous index position
                int c = userInputArray[b];           // value at previous index
                userInputArray[a] = c;               // move value one step to the right
            }

            // Now place the saved last element at the first position
            int a = 0;                               // first index
            int b = lastElementValue;                // wrapped value
            userInputArray[a] = b;                   // place wrapped value at the start
        }

        // Print the shifted array
        System.out.print("Shifted: ");
        for (int arrayIndex = 0; arrayIndex < arraySize; arrayIndex++) {
            System.out.print(userInputArray[arrayIndex]);
            if (arrayIndex < arraySize - 1) {
                System.out.print(" ");
            }
        }

        // Close the scanner
        userInputScanner.close();
    }
}