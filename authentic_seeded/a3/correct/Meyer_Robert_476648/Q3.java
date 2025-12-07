import java.util.Scanner;

public class Q3 {
    public static void main(String[] args) {
        // Create a Scanner to read user input from the console
        Scanner inputScanner = new Scanner(System.in);

        // Prompt the user to enter a line of text
        System.out.print("Enter text: ");

        // Read the entire line of text from the user
        String userInputText = inputScanner.nextLine();

        // Declare intermediate String variables for transformations
        String uppercaseText;
        String finalResultText;

        // Convert the entire string to uppercase
        // Formula: uppercaseText = userInputText.toUpperCase()
        uppercaseText = userInputText.toUpperCase();

        // Replace all spaces with underscores
        // Formula: finalResultText = uppercaseText.replace(' ', '_')
        finalResultText = uppercaseText.replace(' ', '_');

        // Print the final modified string
        System.out.println("Result: " + finalResultText);

        // Close the scanner to free system resources
        inputScanner.close();
    }
}