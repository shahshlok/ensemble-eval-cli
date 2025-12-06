import java.util.Random;
import java.util.Scanner;

public class Q2 {
    public static void main(String[] args) {
        Random rand = new Random();
        int answer = rand.nextInt(100) + 1;
        Scanner scanner = new Scanner(System.in);
        int guess;
        int count = 0;
        do {
            System.out.print("Guess a number (1-100): ");
            guess = scanner.nextInt();
            count++;
            if (guess < answer) {
                System.out.println("Too low!");
            } else if (guess > answer) {
                System.out.println("Too high!");
            }
        } while (guess != answer);
        System.out.println("Correct! You took " + count + " guesses.");
        scanner.close();
    }
}
