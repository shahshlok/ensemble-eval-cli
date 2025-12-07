import java.util.Scanner;

public class Q3 {
    public static void main(String[] args) {
        // Create a Scanner object to read input from the user
        Scanner userInputScanner = new Scanner(System.in);

        // Prompt the user for a line of text
        System.out.print("Enter text: ");

        // Read the entire line of text from the user
        String originalUserInputLine = userInputScanner.nextLine();

        // Create a temporary holder for the uppercase version of the string
        String upperCaseUserInputLine = originalUserInputLine.toUpperCase();

        // Create another temporary holder for the final modified string
        String finalModifiedString = upperCaseUserInputLine.replace(" ", "_");

        // Extra check to be very sure we are not dealing with a null reference
        if (finalModifiedString != null) {
            // Print the result with the required label
            System.out.println("Result: " + finalModifiedString);
        }

        // Close the scanner to avoid resource leaks
        userInputScanner.close();
    }
}