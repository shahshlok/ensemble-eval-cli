import java.util.Scanner;

public class Q3 {
    public static void main(String[] args) {
        // Create a Scanner object to read user input from the console
        Scanner userInputScanner = new Scanner(System.in);

        // Prompt the user to enter a line of text
        System.out.print("Enter text: ");

        // Read the entire line of text entered by the user
        String userInputLine = userInputScanner.nextLine();

        // Create a temporary holder for the uppercase version of the string
        String upperCaseHolder = userInputLine.toUpperCase();

        // Create another temporary holder for the final modified string
        // Replace spaces with underscores
        String modifiedStringHolder = upperCaseHolder.replace(" ", "_");

        // Extra nervous check: ensure modifiedStringHolder is not null before printing
        if (modifiedStringHolder != null) {
            // Print the result with the required format
            System.out.println("Result: " + modifiedStringHolder);
        }

        // Close the Scanner to avoid resource leaks
        userInputScanner.close();
    }
}