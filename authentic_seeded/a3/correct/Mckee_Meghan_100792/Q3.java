import java.util.Scanner;

public class Q3 {
    public static void main(String[] args) {
        // Create a Scanner object to read user input from the console
        Scanner userInputScanner = new Scanner(System.in);

        // Prompt the user to enter a line of text
        System.out.print("Enter text: ");

        // Read the entire line of text entered by the user, including spaces
        String userInputLine = userInputScanner.nextLine();

        // Create a temporary holder for the uppercase version of the string
        String upperCaseString = userInputLine.toUpperCase();

        // Create another temporary holder for the final modified string
        String modifiedStringWithUnderscores = upperCaseString;

        // Replace spaces with underscores only if there is at least one character
        if (modifiedStringWithUnderscores != null) {
            // Replace all space characters with underscores
            modifiedStringWithUnderscores = modifiedStringWithUnderscores.replace(" ", "_");
        }

        // Print the result with the required label
        System.out.println("Result: " + modifiedStringWithUnderscores);

        // Close the scanner to avoid potential resource leaks
        userInputScanner.close();
    }
}