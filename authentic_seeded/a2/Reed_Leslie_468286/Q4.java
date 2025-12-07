import java.util.Scanner;

public class Q4 {
    public static void main(String[] args) {
        // Create a Scanner to read user input from the keyboard
        Scanner userInputScanner = new Scanner(System.in);

        // Prompt the user to enter the height of the triangle
        System.out.print("Enter height: ");

        // Read the height value N from the user
        int triangleHeight = userInputScanner.nextInt();

        // We will use a for loop to go through each row of the triangle
        // The currentRowNumber will go from 1 up to triangleHeight
        for (int currentRowNumber = 1; currentRowNumber <= triangleHeight; currentRowNumber++) {

            // For each row, we need to print a certain number of asterisks
            // Let us define some intermediate math variables
            int a = currentRowNumber;     // a represents the number of stars to print on this row
            int b = 0;                    // b will be used as a counter for how many stars we have printed
            int c = a;                    // c is another representation of the required number of stars

            // Use an inner loop to print 'a' (or 'c') number of asterisks on the current row
            for (b = 0; b < c; b++) {
                // Print a single asterisk without moving to the next line
                System.out.print("*");
            }

            // After printing all asterisks for this row, move to the next line
            System.out.println();
        }

        // Close the Scanner to free system resources
        userInputScanner.close();
    }
}