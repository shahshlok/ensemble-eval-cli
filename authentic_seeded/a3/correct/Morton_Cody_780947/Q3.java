import java.util.Scanner;

public class Q3 {
    public static void main(String[] args) {
        // Step 1: Create a Scanner so we can read input from the user
        Scanner userInputScanner = new Scanner(System.in);

        // Step 2: Ask the user to enter a line of text
        System.out.print("Enter text: ");

        // Step 3: Read the entire line of text from the user
        String userInputText = userInputScanner.nextLine();

        // Step 4: Convert the entire string to uppercase letters
        String upperCaseText = userInputText.toUpperCase();

        // Step 5: Replace all spaces with underscores
        String finalModifiedText = upperCaseText.replace(" ", "_");

        // Step 6: Print the result in the required format
        System.out.println("Result: " + finalModifiedText);

        // Step 7: Close the scanner because we are done with input
        userInputScanner.close();
    }
}