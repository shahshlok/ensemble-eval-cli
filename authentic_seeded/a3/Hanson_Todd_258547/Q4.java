import java.util.Scanner;

public class Q4 {
    public static void main(String[] args) {
        // Create a Scanner object so we can read input from the user
        Scanner userInputScanner = new Scanner(System.in);

        // Ask the user to enter the size of the array
        System.out.print("Enter size: ");

        // Read the size of the array from the user
        int arraySize = userInputScanner.nextInt();

        // Create an array to store the integers entered by the user
        int[] userInputArray = new int[arraySize];

        // Ask the user to enter the elements of the array
        System.out.print("Enter elements: ");

        // Use a loop to read each element and store it in the array
        for (int arrayIndex = 0; arrayIndex < arraySize; arrayIndex++) {
            userInputArray[arrayIndex] = userInputScanner.nextInt();
        }

        // If the array size is 0 or 1, shifting will not change anything
        // but the logic below also handles these cases safely

        // Store the last element because it will wrap around to the front
        int lastElementValue = userInputArray[arraySize - 1];

        // Perform the right shift by moving each element one position to the right
        // We go from right to left to avoid overwriting values we still need
        for (int arrayIndex = arraySize - 1; arrayIndex > 0; arrayIndex--) {
            // Move the element from the previous index to the current index
            int a = arrayIndex;                // current index
            int b = arrayIndex - 1;           // previous index
            int c = userInputArray[b];        // value to move
            userInputArray[a] = c;            // assign the value
        }

        // Put the last element (saved earlier) in the first position
        userInputArray[0] = lastElementValue;

        // Print the shifted array
        System.out.print("Shifted: ");
        for (int arrayIndex = 0; arrayIndex < arraySize; arrayIndex++) {
            System.out.print(userInputArray[arrayIndex]);
            if (arrayIndex < arraySize - 1) {
                System.out.print(" ");
            }
        }

        // Close the scanner to free resources
        userInputScanner.close();
    }
}