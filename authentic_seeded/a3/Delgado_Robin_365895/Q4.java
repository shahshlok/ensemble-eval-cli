import java.util.Scanner;

public class Q4 {
    public static void main(String[] args) {
        // Create a Scanner object to read user input from the keyboard
        Scanner userInputScanner = new Scanner(System.in);

        // Ask the user for the size of the array
        System.out.print("Enter size: ");
        int arraySize = userInputScanner.nextInt();

        // Create an array to store the integers
        int[] userInputArray = new int[arraySize];

        // Ask the user to enter the elements
        System.out.print("Enter elements: ");

        // Read each integer from the user and store it in the array
        for (int arrayIndex = 0; arrayIndex < arraySize; arrayIndex++) {
            userInputArray[arrayIndex] = userInputScanner.nextInt();
        }

        // If the array has at least one element, we perform the right shift
        if (arraySize > 0) {
            // Store the last element because it will wrap around to the front
            int lastElementValue = userInputArray[arraySize - 1];

            // Use intermediate math-like variables for clarity
            int shiftIndexStart = arraySize - 1; // starting index for shifting
            int shiftIndexEnd = 1; // we will reach index 1 because index 0 gets the last element later
            int shiftStep = -1; // we move from right to left (decreasing indices)

            // Shift elements one position to the right
            for (int currentIndex = shiftIndexStart; currentIndex >= shiftIndexEnd; currentIndex += shiftStep) {
                // Move each element to the position to its right
                userInputArray[currentIndex] = userInputArray[currentIndex - 1];
            }

            // Put the last element (saved earlier) into the first position
            userInputArray[0] = lastElementValue;
        }

        // Print the shifted array
        System.out.print("Shifted: ");
        for (int arrayIndex = 0; arrayIndex < arraySize; arrayIndex++) {
            System.out.print(userInputArray[arrayIndex]);
            if (arrayIndex < arraySize - 1) {
                System.out.print(" ");
            }
        }
    }
}