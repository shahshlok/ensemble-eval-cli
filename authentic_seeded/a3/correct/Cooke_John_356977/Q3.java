import java.util.Scanner;

public class Q3 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter text: ");
        String inputLine = scanner.nextLine();
        String upperLine = inputLine.toUpperCase();
        String result = upperLine.replace(" ", "_");

        String outputPrefix = "Result: ";
        String outputLine = outputPrefix + result;

        System.out.println(outputLine);

        scanner.close();
    }
}