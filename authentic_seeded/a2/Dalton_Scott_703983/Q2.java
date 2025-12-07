import java.util.Random;
import java.util.Scanner;

public class Q2 {
  public static void main(String[] args) {
   Scanner input = new Scanner(System.in);
      Random rand = new Random();

		int answer = rand.nextInt(100) + 1;
    int guess_count = 0;
      int guess = 0;

      while (guess != answer) {
        System.out.print("Guess a number (1-100): ");
          guess = input.nextInt();
      guess_count++;

        if (guess < answer) {
      System.out.println("Too low!");
        } else if (guess > answer) {
        	 System.out.println("Too high!");
        }
      }

   System.out.println("Correct! You took " + guess_count + " guesses.");
     input.close();
  }
}