import java.util.Scanner;

public class Q4 {
    public static void main(String[] args) {
        // Step 1: Create a Scanner to read input from the user
        Scanner userInputScanner = new Scanner(System.in);

        // Step 2: Ask the user for the size of the array
        System.out.print("Enter size: ");

        // Step 3: Read the size of the array
        int arraySize = userInputScanner.nextInt();

        // Step 4: Create an integer array with the given size
        int[] userInputArray = new int[arraySize];

        // Step 5: Ask the user to enter the elements of the array
        System.out.print("Enter elements: ");

        // Step 6: Read each element from the user and store it in the array
        for (int arrayIndex = 0; arrayIndex < arraySize; arrayIndex++) {
            userInputArray[arrayIndex] = userInputScanner.nextInt();
        }

        // Step 7: If the array has at least one element, perform the right shift
        if (arraySize > 0) {
            // Step 8: Save the last element because it will be moved to the front
            int lastElementValue = userInputArray[arraySize - 1];

            // Step 9: Move each element one position to the right
            // We start from the end and move backwards to avoid overwriting values
            for (int arrayIndex = arraySize - 1; arrayIndex > 0; arrayIndex--) {
                userInputArray[arrayIndex] = userInputArray[arrayIndex - 1];
            }

            // Step 10: Put the saved last element into the first position
            userInputArray[0] = lastElementValue;
        }

        // Step 11: Print the shifted array
        System.out.print("Shifted: ");
        for (int arrayIndex = 0; arrayIndex < arraySize; arrayIndex++) {
            System.out.print(userInputArray[arrayIndex]);
            if (arrayIndex < arraySize - 1) {
                System.out.print(" ");
            }
        }

        // Step 12: Close the scanner
        userInputScanner.close();
    }
}