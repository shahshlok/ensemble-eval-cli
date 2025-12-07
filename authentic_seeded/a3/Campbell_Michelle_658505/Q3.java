import java.util.Scanner;

public class Q3 {
    public static void main(String[] args) {
        // Create a Scanner object to read user input from the console
        Scanner userInputScanner = new Scanner(System.in);

        // Prompt the user to enter a line of text
        System.out.print("Enter text: ");

        // Read the entire line of text from the user
        String originalUserInputLine = userInputScanner.nextLine();

        // Make sure the originalUserInputLine is not null (extra safety, even though nextLine should not return null)
        if (originalUserInputLine == null) {
            originalUserInputLine = "";
        }

        // Convert the entire string to uppercase using a temporary holder variable
        String upperCaseUserInputLine = originalUserInputLine.toUpperCase();

        // Replace all spaces with underscores using another temporary holder variable
        String finalModifiedUserInputLine = upperCaseUserInputLine.replace(' ', '_');

        // Print the result with the required label
        System.out.println("Result: " + finalModifiedUserInputLine);

        // Close the scanner to be safe with resources
        userInputScanner.close();
    }
}