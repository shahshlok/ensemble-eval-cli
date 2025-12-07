import java.util.Scanner;

public class Q4 {
    public static void main(String[] args) {
        // Create a Scanner object to read user input from the keyboard
        Scanner userInputScanner = new Scanner(System.in);

        // Prompt the user to enter the size of the array
        System.out.print("Enter size: ");
        int arraySize = userInputScanner.nextInt();

        // Create an array to store the integers with the given size
        int[] userInputArray = new int[arraySize];

        // Prompt the user to enter the elements of the array
        System.out.print("Enter elements: ");

        // Read each element from the user and store in the array
        for (int currentIndex = 0; currentIndex < arraySize; currentIndex++) {
            userInputArray[currentIndex] = userInputScanner.nextInt();
        }

        // If the array size is greater than 0, we perform the right shift
        if (arraySize > 0) {
            // Store the last element because it will wrap around to the front
            int lastElementValue = userInputArray[arraySize - 1];

            // We will shift elements one position to the right
            // We move from right to left to avoid overwriting values we still need
            for (int currentIndex = arraySize - 1; currentIndex > 0; currentIndex--) {
                // Move the element at position currentIndex - 1 to position currentIndex
                userInputArray[currentIndex] = userInputArray[currentIndex - 1];
            }

            // Place the previously saved last element at the first position
            userInputArray[0] = lastElementValue;
        }

        // Print the shifted array in the required format
        System.out.print("Shifted: ");
        for (int currentIndex = 0; currentIndex < arraySize; currentIndex++) {
            System.out.print(userInputArray[currentIndex]);
            if (currentIndex < arraySize - 1) {
                System.out.print(" ");
            }
        }

        // Close the scanner to free system resources
        userInputScanner.close();
    }
}