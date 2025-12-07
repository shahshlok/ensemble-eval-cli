import java.util.Scanner;

public class Q3 {
    public static void main(String[] args) {
        // Create a Scanner to read user input from the console
        Scanner userInputScanner = new Scanner(System.in);

        // Prompt the user to enter some text
        System.out.print("Enter text: ");

        // Read the entire line of text entered by the user
        String userInputLine = userInputScanner.nextLine();

        // Create a temporary holder for the uppercase version of the string
        String upperCaseHolder = userInputLine.toUpperCase();

        // Create another temporary holder for the final result after replacing spaces
        String finalResultString = upperCaseHolder;

        // Check that the string is not null before attempting to replace spaces
        if (finalResultString != null) {
            // Replace all spaces with underscores using replace method
            String replacedSpacesHolder = finalResultString.replace(' ', '_');
            finalResultString = replacedSpacesHolder;
        }

        // Print the result in the required format
        System.out.println("Result: " + finalResultString);

        // Close the Scanner to be safe, even though the program is ending
        userInputScanner.close();
    }
}