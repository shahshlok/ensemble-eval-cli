import java.util.Scanner;

public class Q4 {
    public static void main(String[] args) {
        // Create a Scanner to read user input from the keyboard
        Scanner userInputScanner = new Scanner(System.in);

        // Prompt the user to enter the height of the right triangle
        System.out.print("Enter height: ");

        // Read the height value N from the user
        int triangleHeight = userInputScanner.nextInt();

        // We will use a for loop to go through each row from 1 to triangleHeight
        for (int currentRowNumber = 1; currentRowNumber <= triangleHeight; currentRowNumber++) {

            // For each row, we want to print a number of asterisks equal to the row number
            // So if currentRowNumber = 1, print 1 star; if currentRowNumber = 2, print 2 stars, etc.
            int a = currentRowNumber; // a represents how many stars to print on this row

            // Inner loop to print asterisks on the current row
            for (int currentStarCount = 1; currentStarCount <= a; currentStarCount++) {
                System.out.print("*");
            }

            // After printing all asterisks for this row, move to the next line
            System.out.println();
        }

        // Close the Scanner to free system resources
        userInputScanner.close();
    }
}