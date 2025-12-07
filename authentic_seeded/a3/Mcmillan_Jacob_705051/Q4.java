import java.util.Scanner;

public class Q4 {
    public static void main(String[] args) {
        // Step 1: Create a Scanner to read user input from the keyboard
        Scanner userInputScanner = new Scanner(System.in);

        // Step 2: Ask the user for the size of the array
        System.out.print("Enter size: ");
        int arraySize = userInputScanner.nextInt();

        // Step 3: Create an integer array with the specified size
        int[] userInputArray = new int[arraySize];

        // Step 4: Ask the user to enter the elements for the array
        System.out.print("Enter elements: ");

        // Step 5: Read each element from the user and store it in the array
        for (int currentIndex = 0; currentIndex < arraySize; currentIndex++) {
            userInputArray[currentIndex] = userInputScanner.nextInt();
        }

        // Step 6: If the array has at least one element, perform the right shift
        if (arraySize > 0) {
            // Step 7: Store the last element because it will wrap around to the front
            int lastElementValue = userInputArray[arraySize - 1];

            // Step 8: Move each element one position to the right, starting from the end
            for (int currentIndex = arraySize - 1; currentIndex > 0; currentIndex--) {
                userInputArray[currentIndex] = userInputArray[currentIndex - 1];
            }

            // Step 9: Place the last element at the first position to complete the rotation
            userInputArray[0] = lastElementValue;
        }

        // Step 10: Print the shifted array in the required format
        System.out.print("Shifted: ");
        for (int currentIndex = 0; currentIndex < arraySize; currentIndex++) {
            System.out.print(userInputArray[currentIndex]);
            if (currentIndex < arraySize - 1) {
                System.out.print(" ");
            }
        }

        // Step 11: Close the scanner to free up resources
        userInputScanner.close();
    }
}