import java.util.Scanner;

public class Q3 {
    public static void main(String[] args) {
        // Create a Scanner to read user input from the keyboard
        Scanner userInputScanner = new Scanner(System.in);

        // Prompt the user to enter the numeric grade
        System.out.print("Enter grade: ");

        // Read the numeric grade as an integer
        int numericGradeInput = userInputScanner.nextInt();

        // Declare intermediate math variables for the grade boundaries
        int gradeBoundaryA = 90;
        int gradeBoundaryB = 80;
        int gradeBoundaryC = 70;
        int gradeBoundaryD = 60;

        // Declare a String variable to store the resulting letter grade
        String letterGradeResult;

        // Use if-else statements to determine the letter grade based on the numeric value
        if (numericGradeInput >= gradeBoundaryA && numericGradeInput <= 100) {
            // Numeric grade is between 90 and 100 inclusive, so the letter grade is A
            letterGradeResult = "A";
        } else if (numericGradeInput >= gradeBoundaryB && numericGradeInput <= 89) {
            // Numeric grade is between 80 and 89 inclusive, so the letter grade is B
            letterGradeResult = "B";
        } else if (numericGradeInput >= gradeBoundaryC && numericGradeInput <= 79) {
            // Numeric grade is between 70 and 79 inclusive, so the letter grade is C
            letterGradeResult = "C";
        } else if (numericGradeInput >= gradeBoundaryD && numericGradeInput <= 69) {
            // Numeric grade is between 60 and 69 inclusive, so the letter grade is D
            letterGradeResult = "D";
        } else {
            // Numeric grade is below 60, so the letter grade is F
            letterGradeResult = "F";
        }

        // Print the computed letter grade to the user
        System.out.println("Letter grade: " + letterGradeResult);

        // Close the Scanner to free system resources
        userInputScanner.close();
    }
}