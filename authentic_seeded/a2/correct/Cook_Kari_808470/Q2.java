import java.util.Random;
import java.util.Scanner;

public class Q2 {
    public static void main(String[] args) {
        // Create a Scanner object so we can read user input from the keyboard
        Scanner userInputScanner = new Scanner(System.in);

        // Create a Random object so we can generate a random number
        Random randomNumberGenerator = new Random();

        // Generate the secret answer between 1 and 100 using the formula:
        // answer = randomIntegerFrom0To99 + 1
        int randomBaseValue = randomNumberGenerator.nextInt(100);
        int secretAnswerValue = randomBaseValue + 1;

        // Initialize the number of guesses the user has taken so far
        int totalNumberOfGuesses = 0;

        // Initialize the user's current guess value
        int userCurrentGuessValue = 0;

        // Loop until the user guesses the correct secret answer
        while (userCurrentGuessValue != secretAnswerValue) {
            // Prompt the user to guess a number between 1 and 100
            System.out.print("Guess a number (1-100): ");

            // Read the user's guess from the keyboard
            userCurrentGuessValue = userInputScanner.nextInt();

            // Increase the total number of guesses by 1
            int incrementValue = 1;
            totalNumberOfGuesses = totalNumberOfGuesses + incrementValue;

            // Compare the user's guess with the secret answer
            int differenceValue = userCurrentGuessValue - secretAnswerValue;

            // If the difference is less than 0, the guess is too low
            if (differenceValue < 0) {
                System.out.println("Too low!");
            }
            // If the difference is greater than 0, the guess is too high
            else if (differenceValue > 0) {
                System.out.println("Too high!");
            }
            // If the difference is exactly 0, the guess is correct
            else {
                System.out.println("Correct! You took " + totalNumberOfGuesses + " guesses.");
            }
        }

        // Close the Scanner to free system resources
        userInputScanner.close();
    }
}