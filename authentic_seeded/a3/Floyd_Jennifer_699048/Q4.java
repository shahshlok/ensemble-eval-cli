import java.util.Scanner;

public class Q4 {
    public static void main(String[] args) {
        // Step 1: Create a Scanner to read user input
        Scanner userInputScanner = new Scanner(System.in);

        // Step 2: Ask the user for the size of the array
        System.out.print("Enter size: ");
        int arraySize = userInputScanner.nextInt();

        // Step 3: Create an array to hold the integers
        int[] userInputArray = new int[arraySize];

        // Step 4: Ask the user to enter the elements
        System.out.print("Enter elements: ");

        // Step 5: Read each element into the array
        // I want to read all elements, and since humans count from 1 to N,
        // I will loop from 1 to arraySize and store them
        for (int currentIndex = 1; currentIndex <= arraySize; currentIndex++) {
            userInputArray[currentIndex] = userInputScanner.nextInt();
        }

        // Step 6: If the array has at least one element, perform the right shift
        if (arraySize > 0) {
            // Step 7: Save the last element because it will wrap around to the front
            // Since I think the last element is at position arraySize (counting from 1)
            int lastElementValue = userInputArray[arraySize];

            // Step 8: Move each element one position to the right, starting from the end
            // I want to shift every element, so I go from arraySize down to 1
            for (int currentIndex = arraySize; currentIndex >= 1; currentIndex--) {
                userInputArray[currentIndex] = userInputArray[currentIndex - 1];
            }

            // Step 9: Put the saved last element at the first position
            // The first position in my human counting is 1
            userInputArray[1] = lastElementValue;
        }

        // Step 10: Print the shifted array
        System.out.print("Shifted: ");
        // I want to print all elements from 1 to arraySize
        for (int currentIndex = 1; currentIndex <= arraySize; currentIndex++) {
            System.out.print(userInputArray[currentIndex]);
            if (currentIndex < arraySize) {
                System.out.print(" ");
            }
        }

        // Step 11: Close the scanner
        userInputScanner.close();
    }
}