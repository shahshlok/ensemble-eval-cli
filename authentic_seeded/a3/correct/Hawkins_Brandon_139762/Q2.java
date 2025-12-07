import java.util.Scanner;

public class Q2 {
    public static void main(String[] args) {
        // Step 1: Create a Scanner to read user input from the keyboard
        Scanner userInputScanner = new Scanner(System.in);

        // Step 2: Ask the user to enter the number of students
        System.out.print("Enter number of students: ");
        int numberOfStudents = userInputScanner.nextInt();

        // Step 3: Create an array to store the student names
        String[] studentNames = new String[numberOfStudents];

        // Step 4: Create an array to store the student scores
        int[] studentScores = new int[numberOfStudents];

        // Step 5: Ask the user to enter all the names
        System.out.print("Enter names: ");
        // Step 6: Read each name and store it in the studentNames array
        for (int index = 0; index < numberOfStudents; index++) {
            studentNames[index] = userInputScanner.next();
        }

        // Step 7: Ask the user to enter all the scores
        System.out.print("Enter scores: ");
        // Step 8: Read each score and store it in the studentScores array
        for (int index = 0; index < numberOfStudents; index++) {
            studentScores[index] = userInputScanner.nextInt();
        }

        // Step 9: Sort both arrays based on the scores using a simple bubble sort
        // We want the scores in ascending order, so the largest score ends up at the end
        for (int outerIndex = 0; outerIndex < numberOfStudents - 1; outerIndex++) {
            // Step 10: Go through the array and compare adjacent scores
            for (int innerIndex = 0; innerIndex < numberOfStudents - 1 - outerIndex; innerIndex++) {
                // Step 11: If the current score is greater than the next score, swap them
                if (studentScores[innerIndex] > studentScores[innerIndex + 1]) {
                    // Step 12: Swap scores
                    int temporaryScore = studentScores[innerIndex];
                    studentScores[innerIndex] = studentScores[innerIndex + 1];
                    studentScores[innerIndex + 1] = temporaryScore;

                    // Step 13: Swap corresponding names to keep arrays parallel
                    String temporaryName = studentNames[innerIndex];
                    studentNames[innerIndex] = studentNames[innerIndex + 1];
                    studentNames[innerIndex + 1] = temporaryName;
                }
            }
        }

        // Step 14: After sorting, the top student is the last one in the arrays
        String topStudentName = studentNames[numberOfStudents - 1];
        int topStudentScore = studentScores[numberOfStudents - 1];

        // Step 15: Print the top student's name and score in the required format
        System.out.println("Top student: " + topStudentName + " (" + topStudentScore + ")");

        // Step 16: Close the scanner because we are done with input
        userInputScanner.close();
    }
}