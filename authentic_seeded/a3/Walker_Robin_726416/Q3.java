import java.util.Scanner;

public class Q3 {
    public static void main(String[] args) {
        // Create a Scanner object to read user input from the console
        Scanner userInputScanner = new Scanner(System.in);

        // Prompt the user to enter a line of text
        System.out.print("Enter text: ");

        // Read the entire line of text entered by the user
        String originalUserInputLine = userInputScanner.nextLine();

        // Create a temporary holder for the uppercase version of the string
        String upperCaseUserInputLine = originalUserInputLine.toUpperCase();

        // Create another temporary holder for the final modified string
        // Replace all spaces with underscores
        String modifiedUserInputLine = upperCaseUserInputLine.replace(" ", "_");

        // Nervous edge case check: ensure the modified string is not null before printing
        if (modifiedUserInputLine != null) {
            // Print the result with the required label
            System.out.println("Result: " + modifiedUserInputLine);
        }

        // Close the Scanner to avoid resource leaks
        userInputScanner.close();
    }
}