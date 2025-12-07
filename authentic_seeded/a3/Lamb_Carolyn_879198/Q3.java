import java.util.Scanner;

public class Q3 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter text: ");
        String inputLine = scanner.nextLine();
        String upperLine = "";
        String resultLine = "";

        if (inputLine != null) {
            upperLine = inputLine.toUpperCase();
        } else {
            upperLine = "";
        }

        if (upperLine != null) {
            resultLine = upperLine.replace(' ', '_');
        } else {
            resultLine = "";
        }

        System.out.println("Result: " + resultLine);

        scanner.close();
    }
}