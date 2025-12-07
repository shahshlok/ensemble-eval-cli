import java.util.Scanner;

public class Q3 {
    public static void main(String[] args) {
        // Step 1: Create a Scanner so we can read input from the user
        Scanner userInputScanner = new Scanner(System.in);

        // Step 2: Ask the user to enter a line of text
        System.out.print("Enter text: ");

        // Step 3: Read the entire line of text that the user types
        String originalUserInputLine = userInputScanner.nextLine();

        // Step 4: Convert the entire string to uppercase
        String uppercaseUserInputLine = originalUserInputLine.toUpperCase();

        // Step 5: Replace all spaces with underscores
        String finalModifiedUserInputLine = uppercaseUserInputLine.replace(' ', '_');

        // Step 6: Print the result with the required label
        System.out.println("Result: " + finalModifiedUserInputLine);

        // Step 7: Close the Scanner because we are done with input
        userInputScanner.close();
    }
}