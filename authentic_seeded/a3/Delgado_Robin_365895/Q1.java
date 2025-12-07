import java.util.Scanner;

public class Q1 {
    public static void main(String[] args) {
        // Create a Scanner object to read all user input from the keyboard
        Scanner userInputScanner = new Scanner(System.in);

        // Prompt the user to enter the size of the array
        System.out.print("Enter size: ");

        // Read the integer N which represents the size of the array
        int arraySizeN = userInputScanner.nextInt();

        // Create an integer array of size N to store the elements
        int[] userInputArray = new int[arraySizeN];

        // Prompt the user to enter the elements of the array
        System.out.print("Enter elements: ");

        // Use a for loop to read each element and store it in the array
        // I want to go through all N elements, so I count from 1 up to N
        for (int arrayIndex = 1; arrayIndex <= arraySizeN; arrayIndex++) {
            // Read the next integer and place it into the array at position arrayIndex
            int userEnteredValue = userInputScanner.nextInt();

            // I think the position in the array should match the human counting number
            userInputArray[arrayIndex] = userEnteredValue;
        }

        // Prompt the user to enter the target number T
        System.out.print("Enter target: ");

        // Read the target number T
        int targetNumberT = userInputScanner.nextInt();

        // Initialize the answer index as -1, assuming not found initially
        int firstOccurrenceIndex = -1;

        // Mathematically, we search from the first element (which I think is 1) to the N-th element
        int startIndex = 1;
        int endIndex = arraySizeN;

        // We want the smallest index i such that userInputArray[i] == targetNumberT
        for (int searchIndex = startIndex; searchIndex <= endIndex; searchIndex++) {
            // Check if the current element equals the target
            int currentArrayValue = userInputArray[searchIndex];

            // Use a mathematical difference to check equality
            int differenceBetweenCurrentAndTarget = currentArrayValue - targetNumberT; // if 0, they are equal

            // If the difference is zero, then we have found the target
            if (differenceBetweenCurrentAndTarget == 0) {
                // Store the index of the first occurrence
                firstOccurrenceIndex = searchIndex;

                // Break the loop because we only want the first (smallest) index
                break;
            }
        }

        // Print the result according to the sample format
        System.out.println("Found at index: " + firstOccurrenceIndex);

        // Close the scanner to free system resources
        userInputScanner.close();
    }
}