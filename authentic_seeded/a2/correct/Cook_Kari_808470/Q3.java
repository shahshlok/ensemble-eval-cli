import java.util.Scanner;

public class Q3 {
    public static void main(String[] args) {
        // Create a Scanner object to read user input from the keyboard
        Scanner userInputScanner = new Scanner(System.in);

        // Prompt the user to enter the numeric grade
        System.out.print("Enter grade: ");

        // Read the numeric grade as an integer value
        int userInputGradeValue = userInputScanner.nextInt();

        // Declare intermediate variables that might be useful for math-style reasoning
        int lowerBoundA = 90;
        int lowerBoundB = 80;
        int lowerBoundC = 70;
        int lowerBoundD = 60;
        int upperBoundMaximum = 100;

        // Declare a character variable to store the letter grade
        char computedLetterGrade;

        // Use if-else statements to determine the letter grade based on the numeric grade ranges
        if (userInputGradeValue >= lowerBoundA && userInputGradeValue <= upperBoundMaximum) {
            // If the grade is between 90 and 100, it is an A
            computedLetterGrade = 'A';
        } else if (userInputGradeValue >= lowerBoundB) {
            // If the grade is between 80 and 89, it is a B
            computedLetterGrade = 'B';
        } else if (userInputGradeValue >= lowerBoundC) {
            // If the grade is between 70 and 79, it is a C
            computedLetterGrade = 'C';
        } else if (userInputGradeValue >= lowerBoundD) {
            // If the grade is between 60 and 69, it is a D
            computedLetterGrade = 'D';
        } else {
            // If the grade is below 60, it is an F
            computedLetterGrade = 'F';
        }

        // Print the result in the required format
        System.out.println("Letter grade: " + computedLetterGrade);

        // Close the Scanner to free up resources
        userInputScanner.close();
    }
}