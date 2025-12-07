import java.util.Scanner;

public class Q1 {
    public static void main(String[] args) {
        // Step 1: Create a Scanner object to read input from the user
        Scanner userInputScanner = new Scanner(System.in);

        // Step 2: Ask the user to enter the size of the array
        System.out.print("Enter size: ");
        int arraySize = userInputScanner.nextInt();

        // Step 3: Create an array to store the integers
        int[] userInputArray = new int[arraySize];

        // Step 4: Ask the user to enter the elements of the array
        System.out.print("Enter elements: ");
        for (int currentIndex = 0; currentIndex < arraySize; currentIndex++) {
            // Step 5: Read each integer and store it in the array
            userInputArray[currentIndex] = userInputScanner.nextInt();
        }

        // Step 6: Ask the user to enter the target value to search for
        System.out.print("Enter target: ");
        int targetValue = userInputScanner.nextInt();

        // Step 7: Initialize a variable to store the found index, start with -1 meaning "not found"
        int foundIndex = -1;

        // Step 8: Loop through the array to search for the first occurrence of the target value
        for (int currentIndex = 0; currentIndex < arraySize; currentIndex++) {
            // Step 9: Check if the current element equals the target value
            if (userInputArray[currentIndex] == targetValue) {
                // Step 10: If found, store the index and break out of the loop
                foundIndex = currentIndex;
                break;
            }
        }

        // Step 11: Print the result in the required format
        System.out.println("Found at index: " + foundIndex);

        // Step 12: Close the scanner
        userInputScanner.close();
    }
}