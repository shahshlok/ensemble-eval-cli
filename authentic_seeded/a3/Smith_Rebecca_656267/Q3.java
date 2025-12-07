import java.util.Scanner;

public class Q3 {
    public static void main(String[] args) {
        // Create a Scanner to read user input from the console
        Scanner userInputScanner = new Scanner(System.in);

        // Prompt the user to enter a line of text
        System.out.print("Enter text: ");

        // Read the full line of text from the user
        String originalUserInputLine = userInputScanner.nextLine();

        // Make sure the string is not null before proceeding (extra safety check)
        if (originalUserInputLine == null) {
            originalUserInputLine = "";
        }

        // Convert the entire string to uppercase
        String upperCaseUserInputLine = originalUserInputLine.toUpperCase();

        // Replace all spaces with underscores
        String modifiedUserInputLine = upperCaseUserInputLine.replace(" ", "_");

        // Prepare the final result string using a temporary holder variable
        String finalResultToPrint = "Result: " + modifiedUserInputLine;

        // Print the modified string to the console
        System.out.println(finalResultToPrint);

        // Close the Scanner to be safe with resources
        userInputScanner.close();
    }
}