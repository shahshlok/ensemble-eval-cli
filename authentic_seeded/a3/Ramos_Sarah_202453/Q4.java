import java.util.Scanner;

public class Q4 {
    public static void main(String[] args) {
        // Create a Scanner to read input from the user
        Scanner userInputScanner = new Scanner(System.in);

        // Ask the user for the size of the array
        System.out.print("Enter size: ");
        int arraySize = userInputScanner.nextInt();

        // Create an array to store the integers
        int[] userInputArray = new int[arraySize];

        // Ask the user to enter the elements
        System.out.print("Enter elements: ");

        // Read each element into the array
        for (int arrayIndex = 0; arrayIndex < arraySize; arrayIndex++) {
            userInputArray[arrayIndex] = userInputScanner.nextInt();
        }

        // If the array has at least one element, perform the right shift
        if (arraySize > 0) {
            // Save the last element because it will wrap around to the front
            int lastElementValue = userInputArray[arraySize - 1];

            // Shift elements to the right by one position
            // We go from right to left so we do not overwrite values we still need
            for (int arrayIndex = arraySize - 1; arrayIndex > 0; arrayIndex--) {
                // a, b, c are intermediate math-style variables for clarity
                int a = arrayIndex;
                int b = arrayIndex - 1;
                int c = userInputArray[b]; // the value that will move to position a

                userInputArray[a] = c;
            }

            // Place the saved last element at the first position
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

        // Close the scanner
        userInputScanner.close();
    }
}