import java.util.Scanner;

public class Q3 {
    public static void main(String[] args) {
        // Create a Scanner object to read user input from the console
        Scanner userInputScanner = new Scanner(System.in);

        // Prompt the user to enter a line of text
        System.out.print("Enter text: ");

        // Read the entire line of text, including spaces, from the user
        String userInputLine = userInputScanner.nextLine();

        // Create a temporary holder variable to store the uppercase version of the input
        String upperCaseUserInputLine = userInputLine.toUpperCase();

        // Create another temporary variable to store the final result
        // Replace all spaces ' ' with underscores '_'
        String finalModifiedString = upperCaseUserInputLine.replace(' ', '_');

        // Extra nervous check to ensure the final string is not null before printing
        if (finalModifiedString != null) {
            // Print the result with the required label
            System.out.println("Result: " + finalModifiedString);
        }

        // Close the scanner to avoid resource leaks
        userInputScanner.close();
    }
}