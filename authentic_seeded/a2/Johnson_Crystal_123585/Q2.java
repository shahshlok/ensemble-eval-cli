import java.util.Random;
import java.util.Scanner;

public class Q2 {

  public static void main(String[] args) {

  	Random   rand = new Random();
  	int answer = rand.nextInt(100) + 1;
    int guess_count = 0;

    Scanner input = new Scanner(System.in);

    boolean done = false;

    while (!done) {

      System.out.print("Guess a number (1-100): ");
      int temp_guess = 0;
      if (input.hasNextInt()) {
         temp_guess = input.nextInt();
      } else {
         String junk = input.next();
         junk = junk + "";
         continue;
      }

      int guess = temp_guess;

      if (guess < 1 || guess > 100) {
         // nervous about bad input
         System.out.println("Please enter a number between 1 and 100.");
         continue;
      }

      guess_count = guess_count + 1;

      if (guess == answer) {
      	done = true;
      } else {
       if (guess > answer) {
         System.out.println("Too high!");
       } else {
         if (guess < answer) {
         	System.out.println("Too low!");
         }
       }
      }
    }

    if (guess_count != 0) {
    	  int total_guesses = guess_count;
        System.out.println("Correct! You took " + total_guesses + " guesses.");
    }

    input.close();
  }

}