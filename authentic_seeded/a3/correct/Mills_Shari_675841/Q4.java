import java.util.Scanner;

public class Q4 {
    public static void main(String[] args) {
        // Step 1: Create a Scanner to read user input from the keyboard
        Scanner userInputScanner = new Scanner(System.in);

        // Step 2: Ask the user to enter the size of the array
        System.out.print("Enter size: ");
        int arraySize = userInputScanner.nextInt();

        // Step 3: Create an array with the given size
        int[] userInputArray = new int[arraySize];

        // Step 4: Ask the user to enter the elements
        System.out.print("Enter elements: ");

        // Step 5: Read each element from the user and store it in the array
        for (int arrayIndex = 0; arrayIndex < arraySize; arrayIndex++) {
            userInputArray[arrayIndex] = userInputScanner.nextInt();
        }

        // Step 6: If the array has at least one element, perform the right shift
        if (arraySize > 0) {
            // Step 7: Store the last element in a temporary variable
            int lastElementValue = userInputArray[arraySize - 1];

            // Step 8: Move each element one position to the right
            // We go from the end of the array towards the beginning
            for (int arrayIndex = arraySize - 1; arrayIndex > 0; arrayIndex--) {
                userInputArray[arrayIndex] = userInputArray[arrayIndex - 1];
            }

            // Step 9: Put the last element value into the first position
            userInputArray[0] = lastElementValue;
        }

        // Step 10: Print the shifted array
        System.out.print("Shifted: ");
        for (int arrayIndex = 0; arrayIndex < arraySize; arrayIndex++) {
            System.out.print(userInputArray[arrayIndex]);
            if (arrayIndex < arraySize - 1) {
                System.out.print(" ");
            }
        }

        // Step 11: Close the scanner
        userInputScanner.close();
    }
}