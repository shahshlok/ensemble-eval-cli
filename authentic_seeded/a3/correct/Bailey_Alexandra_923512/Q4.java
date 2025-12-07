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
        for (int currentIndex = 0; currentIndex < arraySize; currentIndex++) {
            userInputArray[currentIndex] = keyboardScanner.nextInt();
        }

        // Step 6: Handle the case where the array size is greater than 0
        if (arraySize > 0) {
            // Step 7: Store the last element because it will move to the front
            int lastElementValue = userInputArray[arraySize - 1];

            // Step 8: Shift all elements one position to the right
            // We start from the end and move each element to the next index
            for (int currentIndex = arraySize - 1; currentIndex > 0; currentIndex--) {
                userInputArray[currentIndex] = userInputArray[currentIndex - 1];
            }

            // Step 9: Place the old last element at the first position
            userInputArray[0] = lastElementValue;
        }

        // Step 10: Print the shifted array
        System.out.print("Shifted: ");
        for (int currentIndex = 0; currentIndex < arraySize; currentIndex++) {
            System.out.print(userInputArray[currentIndex]);
            if (currentIndex < arraySize - 1) {
                System.out.print(" ");
            }
        }

        // Step 11: Close the Scanner to free resources
        keyboardScanner.close();
    }
}