import java.util.Scanner;

public class Q3 {
    public static void main(String[] args) {
        // Create a Scanner object to read user input from the keyboard
        Scanner keyboardInput = new Scanner(System.in);

        // Prompt the user to enter a line of text
        System.out.print("Enter text: ");

        // Read the entire line of text entered by the user
        String userInputText = keyboardInput.nextLine();

        // I want to convert the entire string to uppercase letters, so I call the method
        userInputText.toUpperCase();

        // I want to replace all spaces with underscores, so I call the method
        userInputText.replace(" ", "_");

        // Print the result in the required format
        System.out.println("Result: " + userInputText);

        // Close the scanner to free associated resources
        keyboardInput.close();
    }
}