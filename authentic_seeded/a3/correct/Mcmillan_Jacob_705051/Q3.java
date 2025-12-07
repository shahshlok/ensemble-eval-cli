import java.util.Scanner;

public class Q3 {
    public static void main(String[] args) {
        // Step 1: Create a Scanner so we can read a full line of text from the user
        Scanner userInputScanner = new Scanner(System.in);

        // Step 2: Ask the user to enter some text
        System.out.print("Enter text: ");

        // Step 3: Read the whole line of text typed by the user
        String originalUserInputText = userInputScanner.nextLine();

        // Step 4: Convert the entire string to uppercase letters
        String uppercaseUserInputText = originalUserInputText.toUpperCase();

        // Step 5: Replace all space characters with underscore characters
        String finalModifiedText = uppercaseUserInputText.replace(" ", "_");

        // Step 6: Print the result in the required format
        System.out.println("Result: " + finalModifiedText);

        // Step 7: Close the scanner because we are done using it
        userInputScanner.close();
    }
}