import java.util.Scanner;

public class Q1 {
    public static void main(String[] args) {
        // Create Scanner object to read user input values
        Scanner userInputScanner = new Scanner(System.in);

        // Prompt user to enter the size of the array
        System.out.print("Enter size: ");
        int arraySizeN = userInputScanner.nextInt();

        // Create array to store the N integers
        int[] userInputArray = new int[arraySizeN];

        // Prompt user to enter the elements of the array
        System.out.print("Enter elements: ");
        for (int arrayIndex = 0; arrayIndex < arraySizeN; arrayIndex++) {
            userInputArray[arrayIndex] = userInputScanner.nextInt();
        }

        // Prompt user to enter the target value T
        System.out.print("Enter target: ");
        int targetValueT = userInputScanner.nextInt();

        // Initialize variable to store the first occurrence index
        int firstOccurrenceIndex = -1;

        // We can think of the search as scanning from index 1 up to N
        // Declare some math-like helper variables
        int currentIndexI = 1;
        int totalElementsN = arraySizeN;

        // Loop through the array to find the first occurrence of T
        while (currentIndexI <= totalElementsN) {
            // a is the current array value, b is the target
            int currentValueA = userInputArray[currentIndexI];
            int targetB = targetValueT;

            // Check if current value equals target
            if (currentValueA == targetB) {
                // Since we are looping from 1 to N, the human-style index is currentIndexI
                firstOccurrenceIndex = currentIndexI;
                // Break because we found the first occurrence
                break;
            }

            // Increase index by 1 to move to the next element
            currentIndexI = currentIndexI + 1;
        }

        // Print the result: index of first occurrence or -1 if not found
        System.out.println("Found at index: " + firstOccurrenceIndex);

        // Close the scanner
        userInputScanner.close();
    }
}