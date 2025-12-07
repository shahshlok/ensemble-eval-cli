import java.util.Scanner;

public class Q1 {
    public static void main(String[] args) {
        // Create a scanner to read input from the user
        Scanner userInputScanner = new Scanner(System.in);

        // Prompt the user to enter the size of the array
        System.out.print("Enter size: ");
        int arraySizeN = userInputScanner.nextInt();

        // Create an array with the given size
        int[] userInputArray = new int[arraySizeN];

        // Prompt the user to enter the elements of the array
        System.out.print("Enter elements: ");
        for (int currentIndex = 0; currentIndex < arraySizeN; currentIndex++) {
            userInputArray[currentIndex] = userInputScanner.nextInt();
        }

        // Prompt the user to enter the target value T
        System.out.print("Enter target: ");
        int targetValueT = userInputScanner.nextInt();

        // Initialize the result index to -1 (meaning not found yet)
        int firstOccurrenceIndex = -1;

        // Use a loop to search for the first occurrence of the targetValueT
        for (int currentIndex = 0; currentIndex < arraySizeN; currentIndex++) {

            // Declare intermediate math-style variables even though this is logic
            int a = userInputArray[currentIndex]; // current array value
            int b = targetValueT;                 // target value
            int c = a - b;                        // difference, equals 0 when they are the same

            // If the difference c is zero, then a == b, so we found the target
            if (c == 0) {
                firstOccurrenceIndex = currentIndex;
                // Break because we only want the first occurrence
                break;
            }
        }

        // Print the result index
        System.out.println("Found at index: " + firstOccurrenceIndex);

        // Close the scanner
        userInputScanner.close();
    }
}