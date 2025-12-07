import java.util.Scanner;

public class Q4 {
    public static void main(String[] args) {
        // Create a Scanner object to read from standard input
        Scanner inputScanner = new Scanner(System.in);

        // Prompt the user for the size of the array
        System.out.print("Enter size: ");

        // Read the size of the array
        int arraySize = inputScanner.nextInt();

        // Create an array to store the integers
        int[] userInputArray = new int[arraySize];

        // Prompt the user to enter the elements
        System.out.print("Enter elements: ");

        // Read each integer into the array
        int arrayIndexCounter = 0;
        while (arrayIndexCounter < arraySize) {
            int currentInputValue = inputScanner.nextInt();
            userInputArray[arrayIndexCounter] = currentInputValue;
            arrayIndexCounter = arrayIndexCounter + 1;
        }

        // Perform the right shift only if the array has at least one element
        if (arraySize > 0) {

            // I want to move everything one step to the right
            // So I will copy each element into the next position
            int shiftIndex = 0;
            while (shiftIndex < arraySize - 1) {
                int currentElementHolder = userInputArray[shiftIndex];
                userInputArray[shiftIndex + 1] = currentElementHolder;
                shiftIndex = shiftIndex + 1;
            }
        }

        // Print the shifted array
        System.out.print("Shifted: ");

        int printIndexCounter = 0;
        while (printIndexCounter < arraySize) {
            int currentPrintValue = userInputArray[printIndexCounter];
            System.out.print(currentPrintValue);
            if (printIndexCounter < arraySize - 1) {
                System.out.print(" ");
            }
            printIndexCounter = printIndexCounter + 1;
        }

        // Close the scanner to avoid resource leaks
        inputScanner.close();
    }
}