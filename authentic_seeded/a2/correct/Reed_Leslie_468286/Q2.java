import java.util.Random;
import java.util.Scanner;

public class Q2 {
    public static void main(String[] args) {

        // Create Scanner object to read user input from the keyboard
        Scanner userInputScanner = new Scanner(System.in);

        // Create Random object to generate random numbers
        Random randomNumberGenerator = new Random();

        // Generate the secret number between 1 and 100 (inclusive)
        int randomBaseNumber = randomNumberGenerator.nextInt(100);
        int randomShiftNumber = 1;
        int secretNumberAnswer = randomBaseNumber + randomShiftNumber;

        // Variable to store the user's current guess
        int userGuessNumber = 0;

        // Variable to count how many guesses the user has taken
        int totalNumberOfGuesses = 0;

        // Loop until the user's guess equals the secret number
        while (userGuessNumber != secretNumberAnswer) {

            // Prompt the user to guess a number
            System.out.print("Guess a number (1-100): ");

            // Read the user's guess from input
            userGuessNumber = userInputScanner.nextInt();

            // Increase the guess counter by 1 for each guess
            int guessIncrementValue = 1;
            totalNumberOfGuesses = totalNumberOfGuesses + guessIncrementValue;

            // Check if the guess is too low, too high, or correct
            if (userGuessNumber < secretNumberAnswer) {
                // User's guess is smaller than the secret number
                System.out.println("Too low!");
            } else if (userGuessNumber > secretNumberAnswer) {
                // User's guess is larger than the secret number
                System.out.println("Too high!");
            } else {
                // User guessed the secret number correctly
                System.out.println("Correct! You took " + totalNumberOfGuesses + " guesses.");
            }
        }

        // Close the scanner to free system resources
        userInputScanner.close();
    }
}