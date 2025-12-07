import java.util.Scanner;

public class Q3 {
    public static void main(String[] args) {
        // Create a Scanner object to read user input from the keyboard
        Scanner userInputScanner = new Scanner(System.in);

        // Prompt the user to enter a line of text
        System.out.print("Enter text: ");

        // Read the entire line of text from the user
        String userInputLine = userInputScanner.nextLine();

        // Step 1: Convert the entire string to uppercase
        String uppercaseUserInputLine = userInputLine.toUpperCase();

        // Step 2: Replace all spaces with underscores
        String finalResultString = uppercaseUserInputLine.replace(" ", "_");

        // Print the result with the required label
        System.out.println("Result: " + finalResultString);

        // Close the scanner
        userInputScanner.close();
    }
}