import java.util.Scanner;

public class Q4 {
    public static void main(String[] args) {
        // Create a Scanner object to read user input from the console
        Scanner userInputScanner = new Scanner(System.in);

        // Prompt the user to enter the size of the array
        System.out.print("Enter size: ");

        // Read the size of the array from user input
        int arraySize = userInputScanner.nextInt();

        // Create an array to store the integers entered by the user
        int[] userInputArray = new int[arraySize];

        // Prompt the user to enter the elements of the array
        System.out.print("Enter elements: ");

        // Use a loop to read each element into the array
        int currentIndex = 0;
        while (currentIndex < arraySize) {
            int userInputValue = userInputScanner.nextInt();
            userInputArray[currentIndex] = userInputValue;
            currentIndex = currentIndex + 1;
        }

        // Perform the right shift only if the array size is greater than 0
        if (arraySize > 0) {
            // I want to shift everything to the right by one position
            // I will start from index 0 and move each element to the next index
            int shiftIndex = 0;
            while (shiftIndex < arraySize - 1) {
                // Calculate the next index where we want to move the value
                int nextIndex = shiftIndex + 1;

                // Extra safety check to make sure we are not going out of bounds
                if (nextIndex < arraySize) {
                    // Move the current element to the next position
                    int temporaryHolderCurrentValue = userInputArray[shiftIndex];
                    userInputArray[nextIndex] = temporaryHolderCurrentValue;
                }

                // Move to the next index
                shiftIndex = shiftIndex + 1;
            }
        }

        // Print the modified array after shifting
        System.out.print("Shifted: ");
        int printIndex = 0;
        while (printIndex < arraySize) {
            int valueToPrint = userInputArray[printIndex];
            System.out.print(valueToPrint);
            if (printIndex != arraySize - 1) {
                System.out.print(" ");
            }
            printIndex = printIndex + 1;
        }

        // Close the scanner to avoid resource leaks
        userInputScanner.close();
    }
}