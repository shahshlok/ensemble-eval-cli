import java.util.Scanner;

public class Q3 {
    public static void main(String[] args) {
        // Step 1: Create a Scanner so we can read input from the user
        Scanner userInputScanner = new Scanner(System.in);

        // Step 2: Ask the user to enter a line of text
        System.out.print("Enter text: ");

        // Step 3: Read the entire line of text from the user
        String userInputLine = userInputScanner.nextLine();

        // Step 4: Convert the whole string to uppercase letters
        String uppercaseUserInputLine = userInputLine.toUpperCase();

        // Step 5: Replace all spaces in the string with underscores
        String finalModifiedString = uppercaseUserInputLine.replace(" ", "_");

        // Step 6: Print the result to the user
        System.out.println("Result: " + finalModifiedString);

        // Step 7: Close the Scanner because we are done with input
        userInputScanner.close();
    }
}