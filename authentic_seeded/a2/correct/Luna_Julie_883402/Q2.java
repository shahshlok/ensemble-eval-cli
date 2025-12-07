import java.util.Scanner;
import java.util.Random;

public class Q2 {

  public static void main(String[] args) {
      Scanner in = new Scanner(System.in);
   Random rand = new Random();

		int a = 1;
   int b = 100;
      int c = b - a + 1;

		int answer = rand.nextInt(c) + a;

   int guess_count = 0;
      int guess = 0;

		while (guess != answer) {

      System.out.print("Guess a number (1-100): ");
         guess = in.nextInt();
      guess_count = guess_count + 1;

         int diff = guess - answer;

      if (diff == 0) {
            System.out.println("Correct! You took " + guess_count + " guesses.");
      } else if (diff > 0) {
      System.out.println("Too high!");
		} else {
         System.out.println("Too low!");
      }

		}

      in.close();
  }

}