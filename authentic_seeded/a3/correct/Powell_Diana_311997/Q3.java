import java.util.Scanner;

public class Q3 {
    public static void main(String[] args) {
        // Create a Scanner object to read user input from the console
        Scanner userInputScanner = new Scanner(System.in);

        // Prompt the user to enter a line of text
        System.out.print("Enter text: ");

        // Read the entire line of text entered by the user
        String originalUserInputLine = userInputScanner.nextLine();

        // Make sure the input is not null before processing (extra edge case check)
        if (originalUserInputLine != null) {
            // Convert the entire string to uppercase
            String upperCaseUserInputLine = originalUserInputLine.toUpperCase();

            // Replace all spaces with underscores
            String upperCaseWithUnderscores = upperCaseUserInputLine.replace(" ", "_");

            // Create a temporary holder for the final result to be printed
            String finalResultString = upperCaseWithUnderscores;

            // Print the modified string with the required label
            System.out.println("Result: " + finalResultString);
        } else {
            // If somehow the input is null, print an empty result just to be safe
            String emptyResultString = "";
            System.out.println("Result: " + emptyResultString);
        }

        // Close the scanner to avoid potential resource leaks
        userInputScanner.close();
    }
}