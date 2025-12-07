import java.util.Random;
import java.util.Scanner;

public class Q2 {

  public static void main(String[] args) {
  	Random rand = new Random();
    int temp_answer = rand.nextInt(100) + 1;
    int answer = temp_answer;
    
    Scanner input_scanner = new Scanner(System.in);

    int guess_count = 0;
    int userGuess = 0;

    boolean done = false;

    while (!done) {

      System.out.print("Guess a number (1-100): ");
      
      if (input_scanner.hasNextInt()) {
      	int temp_guess = input_scanner.nextInt();
        userGuess = temp_guess;
      } else {
      	String junk = input_scanner.next();
        continue;
      }

      if (userGuess < 1 || userGuess > 100) {
        System.out.println("Please enter a number between 1 and 100.");
        continue;
      }

      guess_count = guess_count + 1;

      if (userGuess == answer) {
      	done = true;
      } else {
      	if (userGuess > answer) {
      		System.out.println("Too high!");
      	} else {
      		if (userGuess < answer) {
      			System.out.println("Too low!");
      		}
      	}
      }
    }

    
    if (guess_count != 0) {
      System.out.println("Correct! You took " + guess_count + " guesses.");
    }

    input_scanner.close();
  }
}