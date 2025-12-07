import java.util.Scanner;

public class Q4 {
    public static void main(String[] args) {
        // Step 1: Create a Scanner to read user input from the keyboard
        Scanner keyboardScanner = new Scanner(System.in);

        // Step 2: Ask the user to enter the size of the array
        System.out.print("Enter size: ");
        int arraySize = keyboardScanner.nextInt();

        // Step 3: Create an integer array with the given size
        int[] userInputArray = new int[arraySize];

        // Step 4: Ask the user to enter the elements of the array
        System.out.print("Enter elements: ");

        // Step 5: Read each element from the user and store it in the array
        // I want to cover all elements, so I go from 1 to arraySize
        for (int humanIndex = 1; humanIndex <= arraySize; humanIndex++) {
            // I think the first element is at index 1, second at 2, ..., last at arraySize
            userInputArray[humanIndex] = keyboardScanner.nextInt();
        }

        // Step 6: Handle the case where the array size is greater than 0
        if (arraySize > 0) {
            // Step 7: Store the last element because it will move to the front
            // The last element is at position arraySize (human counting)
            int lastElementValue = userInputArray[arraySize];

            // Step 8: Shift all elements one position to the right
            // I want to move each element to the next position, starting from the last one
            for (int humanIndex = arraySize; humanIndex >= 1; humanIndex--) {
                // Move the element from the previous human position to the current one
                userInputArray[humanIndex] = userInputArray[humanIndex - 1];
            }

            // Step 9: Place the old last element at the first position (position 1 in human terms)
            userInputArray[1] = lastElementValue;
        }

        // Step 10: Print the shifted array
        System.out.print("Shifted: ");
        // I print from 1 to arraySize to cover all elements
        for (int humanIndex = 1; humanIndex <= arraySize; humanIndex++) {
            System.out.print(userInputArray[humanIndex]);
            if (humanIndex < arraySize) {
                System.out.print(" ");
            }
        }

        // Step 11: Close the Scanner to free resources
        keyboardScanner.close();
    }
}