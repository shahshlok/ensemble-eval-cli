import java.util.Scanner;

public class Q3 {
    public static void main(String[] args) {
        // Create a Scanner object to read user input from the console
        Scanner userInputScanner = new Scanner(System.in);

        // Prompt the user to enter text
        System.out.print("Enter text: ");

        // Read the entire line of text entered by the user
        String userInputLine = userInputScanner.nextLine();

        // Create a temporary holder for the uppercase version of the string
        String upperCaseHolder = userInputLine.toUpperCase();

        // Another temporary holder to store the final modified string
        String modifiedStringHolder = upperCaseHolder;

        // Replace spaces with underscores, but check string is not null just in case
        if (modifiedStringHolder != null) {
            // Use replace method to change all spaces to underscores
            String replacedSpacesHolder = modifiedStringHolder.replace(' ', '_');
            modifiedStringHolder = replacedSpacesHolder;
        }

        // Print the final result to the user
        System.out.println("Result: " + modifiedStringHolder);

        // Close the scanner to avoid resource leaks
        userInputScanner.close();
    }
}