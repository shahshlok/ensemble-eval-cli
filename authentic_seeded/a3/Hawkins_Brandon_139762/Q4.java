import java.util.Scanner;

public class Q4 {
    public static void main(String[] args) {
        // Step 1: Create a Scanner object to read user input from the keyboard
        Scanner userInputScanner = new Scanner(System.in);

        // Step 2: Ask the user to enter the size of the array
        System.out.print("Enter size: ");

        // Step 3: Read the size of the array from the user
        int arraySize = userInputScanner.nextInt();

        // Step 4: Create an integer array with the given size
        int[] userInputArray = new int[arraySize];

        // Step 5: Ask the user to enter the elements of the array
        System.out.print("Enter elements: ");

        // Step 6: Use a loop to read each element into the array
        for (int arrayIndex = 0; arrayIndex < arraySize; arrayIndex++) {
            userInputArray[arrayIndex] = userInputScanner.nextInt();
        }

        // Step 7: If the array size is greater than 0, perform the right shift
        if (arraySize > 0) {
            // Step 8: Store the last element in a temporary variable because it will move to the front
            int lastElementValue = userInputArray[arraySize - 1];

            // Step 9: Move each element one position to the right, starting from the end
            for (int arrayIndex = arraySize - 1; arrayIndex > 0; arrayIndex--) {
                userInputArray[arrayIndex] = userInputArray[arrayIndex - 1];
            }

            // Step 10: Put the original last element at the first position
            userInputArray[0] = lastElementValue;
        }

        // Step 11: Print the shifted array elements
        System.out.print("Shifted: ");
        for (int arrayIndex = 0; arrayIndex < arraySize; arrayIndex++) {
            System.out.print(userInputArray[arrayIndex]);
            if (arrayIndex < arraySize - 1) {
                System.out.print(" ");
            }
        }

        // Step 12: Close the Scanner
        userInputScanner.close();
    }
}