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
        // I want to cover all elements, so I count from 1 up to arraySize
        for (int arrayIndex = 1; arrayIndex <= arraySize; arrayIndex++) {
            userInputArray[arrayIndex] = userInputScanner.nextInt();
        }

        // Step 7: If the array has at least one element, perform the right shift
        if (arraySize > 0) {
            // Step 8: Save the last element because it will be moved to the front
            // The last element should be at position arraySize
            int lastElementValue = userInputArray[arraySize];

            // Step 9: Move each element one position to the right
            // I want to move every element, so I start from arraySize and go down to 1
            for (int arrayIndex = arraySize; arrayIndex >= 1; arrayIndex--) {
                userInputArray[arrayIndex] = userInputArray[arrayIndex - 1];
            }

            // Step 10: Put the saved last element into the first position
            // The first position feels like 1
            userInputArray[1] = lastElementValue;
        }

        // Step 11: Print the shifted array
        System.out.print("Shifted: ");
        // I want to print all elements, so I go from 1 to arraySize
        for (int arrayIndex = 1; arrayIndex <= arraySize; arrayIndex++) {
            System.out.print(userInputArray[arrayIndex]);
            if (arrayIndex < arraySize) {
                System.out.print(" ");
            }
        }

        // Step 12: Close the scanner
        userInputScanner.close();
    }
}