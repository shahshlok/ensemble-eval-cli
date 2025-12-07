import java.util.Scanner;

public class Q1 {
    public static void main(String[] args) {
        // Create a Scanner object to read user input
        Scanner userInputScanner = new Scanner(System.in);

        // Prompt the user to enter the size of the array
        System.out.print("Enter size: ");
        int arraySizeN = userInputScanner.nextInt();

        // Create an array to store the integers
        int[] userInputArray = new int[arraySizeN];

        // Prompt the user to enter the elements of the array
        System.out.print("Enter elements: ");

        // Read each element from the user and store it in the array
        for (int arrayIndex = 0; arrayIndex < arraySizeN; arrayIndex++) {
            userInputArray[arrayIndex] = userInputScanner.nextInt();
        }

        // Prompt the user to enter the target integer T
        System.out.print("Enter target: ");
        int targetValueT = userInputScanner.nextInt();

        // Initialize the result index to -1 (meaning not found yet)
        int firstOccurrenceIndex = -1;

        // We will loop through the array and look for the first occurrence of T
        // Mathematically, we want the smallest index i such that userInputArray[i] == targetValueT
        for (int arrayIndex = 0; arrayIndex < arraySizeN; arrayIndex++) {
            // Declare some intermediate math variables to help explain the logic
            int currentIndexI = arrayIndex;
            int currentArrayValueA = userInputArray[currentIndexI];
            int targetValueB = targetValueT;

            // Check if the current value equals the target
            if (currentArrayValueA == targetValueB) {
                // If we find it, store the index and break the loop (first occurrence)
                firstOccurrenceIndex = currentIndexI;
                break;
            }
        }

        // Print the result in the required format
        System.out.println("Found at index: " + firstOccurrenceIndex);

        // Close the scanner to avoid resource leaks
        userInputScanner.close();
    }
}