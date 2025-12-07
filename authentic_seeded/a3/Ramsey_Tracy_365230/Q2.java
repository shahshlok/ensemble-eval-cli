import java.util.Scanner;

public class Q2 {
    public static void main(String[] args) {
        // Step 1: Create a Scanner to read input from the user
        Scanner userInputScanner = new Scanner(System.in);

        // Step 2: Ask the user for the number of students
        System.out.print("Enter number of students: ");
        int numberOfStudents = userInputScanner.nextInt();

        // Step 3: Create arrays to store names and scores based on the number of students
        String[] studentNames = new String[numberOfStudents];
        int[] studentScores = new int[numberOfStudents];

        // Step 4: Read all the student names
        System.out.print("Enter names: ");
        for (int index = 0; index < numberOfStudents; index++) {
            // Read each name as a single word (no spaces inside a name)
            studentNames[index] = userInputScanner.next();
        }

        // Step 5: Read all the student scores
        System.out.print("Enter scores: ");
        for (int index = 0; index < numberOfStudents; index++) {
            // Read each score as an integer
            studentScores[index] = userInputScanner.nextInt();
        }

        // Step 6: Sort both arrays based on scores in ascending order using a simple bubble sort
        // We keep names and scores in sync so that each name still matches its score
        for (int outerIndex = 0; outerIndex < numberOfStudents - 1; outerIndex++) {
            for (int innerIndex = 0; innerIndex < numberOfStudents - 1 - outerIndex; innerIndex++) {
                // If the current score is greater than the next score, swap them
                if (studentScores[innerIndex] > studentScores[innerIndex + 1]) {
                    // Swap scores
                    int temporaryScore = studentScores[innerIndex];
                    studentScores[innerIndex] = studentScores[innerIndex + 1];
                    studentScores[innerIndex + 1] = temporaryScore;

                    // Swap corresponding names to keep parallel arrays consistent
                    String temporaryName = studentNames[innerIndex];
                    studentNames[innerIndex] = studentNames[innerIndex + 1];
                    studentNames[innerIndex + 1] = temporaryName;
                }
            }
        }

        // Step 7: After sorting in ascending order, the highest score is at the last index
        int indexOfTopStudent = numberOfStudents - 1;
        String topStudentName = studentNames[indexOfTopStudent];
        int topStudentScore = studentScores[indexOfTopStudent];

        // Step 8: Print the name and score of the top student in the required format
        System.out.println("Top student: " + topStudentName + " (" + topStudentScore + ")");

        // Step 9: Close the Scanner
        userInputScanner.close();
    }
}