import java.util.Scanner;

public class Q3 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter text: ");
        String inputLine = scanner.nextLine();

        String upperLine = inputLine;
        upperLine.toUpperCase();

        String result = upperLine;
        result.replace(' ', '_');

        String outputPrefix = "Result: ";
        String finalOutput = outputPrefix + result;

        System.out.println(finalOutput);

        scanner.close();
    }
}