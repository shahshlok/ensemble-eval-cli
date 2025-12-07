import java.util.Scanner;

public class Q3 {
    public static void main(String[] args) {
        // Create a Scanner object to read user input from the console
        Scanner userInputScanner = new Scanner(System.in);

        // Prompt the user to enter a line of text
        System.out.print("Enter text: ");

        // Read the entire line of text entered by the user
        String userInputText = userInputScanner.nextLine();

        // Convert the entire string to uppercase letters
        String uppercaseUserInputText = userInputText.toUpperCase();

        // Replace all spaces with underscores in the uppercase string
        String modifiedUserInputText = uppercaseUserInputText.replace(" ", "_");

        // Print the final modified string with the required label
        System.out.println("Result: " + modifiedUserInputText);

        // Close the scanner to free up resources
        userInputScanner.close();
    }
}