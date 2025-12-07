import java.util.Scanner;

public class Q3 {
    public static void main(String[] args) {
        // Step 1: Create a Scanner so we can read input from the user
        Scanner userInputScanner = new Scanner(System.in);

        // Step 2: Prompt the user to enter a line of text
        System.out.print("Enter text: ");

        // Step 3: Read the entire line of text from the user
        String originalUserText = userInputScanner.nextLine();

        // Step 4: Convert the entire string to uppercase letters
        String upperCaseUserText = originalUserText.toUpperCase();

        // Step 5: Replace all spaces in the string with underscores
        String modifiedUserText = upperCaseUserText.replace(' ', '_');

        // Step 6: Print the final modified string with the required label
        System.out.println("Result: " + modifiedUserText);

        // Step 7: Close the scanner because we are done using it
        userInputScanner.close();
    }
}