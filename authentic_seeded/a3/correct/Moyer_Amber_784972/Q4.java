import java.util.Scanner;

public class Q4 {
    public static void main(String[] args) {
        // Create a Scanner object to read input from the user
        Scanner userInputScanner = new Scanner(System.in);

        // Ask the user for the size of the array
        System.out.print("Enter size: ");
        int arraySize = userInputScanner.nextInt();

        // Create an array to store the integers
        int[] userInputArray = new int[arraySize];

        // Ask the user to enter the elements
        System.out.print("Enter elements: ");

        // Read the elements into the array one by one
        for (int currentIndex = 0; currentIndex < arraySize; currentIndex++) {
            userInputArray[currentIndex] = userInputScanner.nextInt();
        }

        // If the array has at least one element, perform the right shift
        if (arraySize > 0) {
            // Store the last element because it will move to the front
            int lastElementValue = userInputArray[arraySize - 1];

            // Move each element one position to the right
            // Start from the end and move backwards
            for (int currentIndex = arraySize - 1; currentIndex > 0; currentIndex--) {
                userInputArray[currentIndex] = userInputArray[currentIndex - 1];
            }

            // Place the last element at the first position
            userInputArray[0] = lastElementValue;
        }

        // Print the shifted array
        System.out.print("Shifted: ");
        for (int currentIndex = 0; currentIndex < arraySize; currentIndex++) {
            System.out.print(userInputArray[currentIndex]);
            if (currentIndex < arraySize - 1) {
                System.out.print(" ");
            }
        }

        // Close the Scanner
        userInputScanner.close();
    }
}