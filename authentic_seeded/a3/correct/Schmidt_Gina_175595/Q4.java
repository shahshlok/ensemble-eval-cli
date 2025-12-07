import java.util.Scanner;

public class Q4 {
    public static void main(String[] args) {
        // Step 1: Create a Scanner to read input from the user
        Scanner userInputScanner = new Scanner(System.in);

        // Step 2: Ask the user to enter the size of the array
        System.out.print("Enter size: ");
        int arraySize = userInputScanner.nextInt();

        // Step 3: Create an array to store the integers
        int[] userInputArray = new int[arraySize];

        // Step 4: Ask the user to enter the elements of the array
        System.out.print("Enter elements: ");
        for (int arrayIndex = 0; arrayIndex < arraySize; arrayIndex++) {
            userInputArray[arrayIndex] = userInputScanner.nextInt();
        }

        // Step 5: If the array has at least one element, perform the right shift
        if (arraySize > 0) {
            // Step 5a: Store the last element since it will wrap around to the front
            int lastElementValue = userInputArray[arraySize - 1];

            // Step 5b: Move each element one position to the right
            for (int arrayIndex = arraySize - 1; arrayIndex > 0; arrayIndex--) {
                userInputArray[arrayIndex] = userInputArray[arrayIndex - 1];
            }

            // Step 5c: Put the last element at the first position
            userInputArray[0] = lastElementValue;
        }

        // Step 6: Print the shifted array
        System.out.print("Shifted: ");
        for (int arrayIndex = 0; arrayIndex < arraySize; arrayIndex++) {
            System.out.print(userInputArray[arrayIndex]);
            if (arrayIndex < arraySize - 1) {
                System.out.print(" ");
            }
        }

        // Step 7: Close the scanner
        userInputScanner.close();
    }
}