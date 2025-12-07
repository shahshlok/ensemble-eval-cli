import java.util.Scanner;

public class Q3 {
    public static void main(String[] args) {

        // Step 1: Create a Scanner so we can read a full line of text from the user
        Scanner userInputScanner = new Scanner(System.in);

        // Step 2: Ask the user to enter some text
        System.out.print("Enter text: ");

        // Step 3: Read the entire line of text the user types
        String userInputLine = userInputScanner.nextLine();

        // Step 4: Convert the entire string to uppercase letters
        userInputLine.toUpperCase();

        // Step 5: Replace all spaces in the string with underscores
        userInputLine.replace(" ", "_");

        // Step 6: Print the final modified string with the required label
        System.out.println("Result: " + userInputLine);

        // Step 7: Close the Scanner because we are done with input
        userInputScanner.close();
    }
}