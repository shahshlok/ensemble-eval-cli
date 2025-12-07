import java.util.Random;
import java.util.Scanner;

public class Q2 {
    public static void main(String[] args) {
        // Step 1: Create a Scanner to read user input from the keyboard
        Scanner userInputScanner = new Scanner(System.in);

        // Step 2: Create a Random object to generate the secret number
        Random randomNumberGenerator = new Random();

        // Step 3: Generate the secret number between 1 and 100 (inclusive)
        int secretAnswerNumber = randomNumberGenerator.nextInt(100) + 1;

        // Step 4: Create variables to store the user's guess and the count of guesses
        int userGuessNumber = 0;
        int totalNumberOfGuesses = 0;

        // Step 5: Use a loop to keep asking the user for guesses until they are correct
        while (userGuessNumber != secretAnswerNumber) {
            // Step 5a: Ask the user to guess a number between 1 and 100
            System.out.print("Guess a number (1-100): ");

            // Step 5b: Read the user's guess from the keyboard
            userGuessNumber = userInputScanner.nextInt();

            // Step 5c: Increase the number of guesses by 1
            totalNumberOfGuesses++;

            // Step 5d: Check if the guess is too low, too high, or correct
            if (userGuessNumber < secretAnswerNumber) {
                // The guess is smaller than the secret number
                System.out.println("Too low!");
            } else if (userGuessNumber > secretAnswerNumber) {
                // The guess is bigger than the secret number
                System.out.println("Too high!");
            } else {
                // The guess is exactly the secret number
                System.out.println("Correct! You took " + totalNumberOfGuesses + " guesses.");
            }
        }

        // Step 6: Close the Scanner because we are done using it
        userInputScanner.close();
    }
}