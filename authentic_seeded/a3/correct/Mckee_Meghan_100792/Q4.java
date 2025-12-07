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
            // Store the last element in a temporary holder variable
            int temporaryLastElementHolder = userInputArray[arraySize - 1];

            // Shift elements one position to the right
            int shiftIndex = arraySize - 1;
            while (shiftIndex > 0) {
                int previousIndex = shiftIndex - 1;
                if (previousIndex >= 0) { // Extra safety check
                    int temporaryHolder = userInputArray[previousIndex];
                    userInputArray[shiftIndex] = temporaryHolder;
                }
                shiftIndex = shiftIndex - 1;
            }

            // Place the last element at the first position
            userInputArray[0] = temporaryLastElementHolder;
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