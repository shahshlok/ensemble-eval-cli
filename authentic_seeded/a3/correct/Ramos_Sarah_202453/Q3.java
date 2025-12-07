import java.util.Scanner;

public class Q3 {
    public static void main(String[] args) {
        // Create a Scanner object to read user input from the keyboard
        Scanner keyboardInput = new Scanner(System.in);

        // Prompt the user to enter a line of text
        System.out.print("Enter text: ");

        // Read the entire line of text entered by the user
        String userInputText = keyboardInput.nextLine();

        // Convert the entire string to uppercase letters
        String upperCaseText = userInputText.toUpperCase();

        // Replace all spaces with underscores
        String finalModifiedText = upperCaseText.replace(" ", "_");

        // Print the result in the required format
        System.out.println("Result: " + finalModifiedText);

        // Close the scanner to free associated resources
        keyboardInput.close();
    }
}