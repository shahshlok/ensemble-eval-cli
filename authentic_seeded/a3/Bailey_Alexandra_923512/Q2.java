import java.util.Scanner;

public class Q2 {
    public static void main(String[] args) {
        // Step 1: Create a Scanner object to read user input from the keyboard
        Scanner userInputScanner = new Scanner(System.in);

        // Step 2: Ask the user to enter the number of students
        System.out.print("Enter number of students: ");
        int numberOfStudents = userInputScanner.nextInt();

        // Step 3: Create an array to store the student names
        String[] studentNames = new String[numberOfStudents];

        // Step 4: Create an array to store the student scores
        int[] studentScores = new int[numberOfStudents];

        // Step 5: Prompt the user to enter all the student names on one line
        System.out.print("Enter names: ");
        // Step 6: Read each student name into the studentNames array
        for (int index = 0; index < numberOfStudents; index++) {
            studentNames[index] = userInputScanner.next();
        }

        // Step 7: Prompt the user to enter all the student scores on one line
        System.out.print("Enter scores: ");
        // Step 8: Read each student score into the studentScores array
        for (int index = 0; index < numberOfStudents; index++) {
            studentScores[index] = userInputScanner.nextInt();
        }

        // Step 9: Sort the parallel arrays based on scores in ascending order
        // We will use a simple bubble sort that swaps both scores and names together
        for (int outerIndex = 0; outerIndex < numberOfStudents - 1; outerIndex++) {
            for (int innerIndex = 0; innerIndex < numberOfStudents - 1 - outerIndex; innerIndex++) {
                // Step 10: Compare the current score with the next score
                if (studentScores[innerIndex] > studentScores[innerIndex + 1]) {
                    // Step 11: Swap the scores
                    int temporaryScore = studentScores[innerIndex];
                    studentScores[innerIndex] = studentScores[innerIndex + 1];
                    studentScores[innerIndex + 1] = temporaryScore;

                    // Step 12: Swap the corresponding names so the parallel arrays stay aligned
                    String temporaryName = studentNames[innerIndex];
                    studentNames[innerIndex] = studentNames[innerIndex + 1];
                    studentNames[innerIndex + 1] = temporaryName;
                }
            }
        }

        // Step 13: After sorting in ascending order, the highest score is at the last position
        int highestScoreIndex = numberOfStudents - 1;
        String topStudentName = studentNames[highestScoreIndex];
        int topStudentScore = studentScores[highestScoreIndex];

        // Step 14: Print the top student's name and score in the required format
        System.out.println("Top student: " + topStudentName + " (" + topStudentScore + ")");

        // Step 15: Close the scanner because we are done with input
        userInputScanner.close();
    }
}