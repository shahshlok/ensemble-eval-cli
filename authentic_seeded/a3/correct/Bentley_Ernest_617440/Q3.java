import java.util.Scanner;

public class Q3 {
    public static void main(String[] args) {
        // Create a Scanner to read input from the user
        Scanner userInputScanner = new Scanner(System.in);

        // Prompt the user for a line of text
        System.out.print("Enter text: ");

        // Read the entire line of text from the user
        String originalUserInputLine = userInputScanner.nextLine();

        // Make sure the original string is not null (extra cautious, even though nextLine should not return null)
        if (originalUserInputLine == null) {
            originalUserInputLine = "";
        }

        // Convert the entire string to uppercase
        String upperCaseUserInputLine = originalUserInputLine.toUpperCase();

        // Replace all spaces with underscores
        // Using a temporary holder variable for clarity and safety
        String modifiedUserInputLineWithUnderscores = upperCaseUserInputLine.replace(" ", "_");

        // Just to be extra safe, check that our result is not null before printing
        if (modifiedUserInputLineWithUnderscores == null) {
            modifiedUserInputLineWithUnderscores = "";
        }

        // Print the final modified string
        System.out.println("Result: " + modifiedUserInputLineWithUnderscores);

        // Close the scanner to avoid resource leaks
        userInputScanner.close();
    }
}