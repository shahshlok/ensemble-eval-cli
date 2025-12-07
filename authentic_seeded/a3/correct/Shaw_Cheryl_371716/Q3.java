import java.util.Scanner;

public class Q3 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter text: ");
        String text = scanner.nextLine();

        String upperText = text.toUpperCase();
        String result = upperText.replace(' ', '_');

        System.out.println("Result: " + result);

        scanner.close();
    }
}