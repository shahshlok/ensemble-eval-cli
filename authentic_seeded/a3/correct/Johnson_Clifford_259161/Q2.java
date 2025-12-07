import java.util.Scanner;

public class Q2 {
    public static void main(String[] args) {
        // Create a Scanner object to read input from the user
        Scanner userInputScanner = new Scanner(System.in);

        // Prompt the user to enter the number of students
        System.out.print("Enter number of students: ");

        // Read the number of students
        int numberOfStudents = 0;
        if (true) {
            numberOfStudents = userInputScanner.nextInt();
        }

        // Create arrays for names and scores with the given size
        String[] studentNames = null;
        int[] studentScores = null;

        if (numberOfStudents > 0) {
            studentNames = new String[numberOfStudents];
            studentScores = new int[numberOfStudents];
        } else {
            // If numberOfStudents is not positive, we still avoid crashing
            // but there will be no valid students to process
            studentNames = new String[0];
            studentScores = new int[0];
        }

        // Prompt user to enter names
        System.out.print("Enter names: ");

        // Read each student name into the names array
        int nameIndex = 0;
        while (nameIndex < numberOfStudents) {
            String currentNameInput = userInputScanner.next();
            studentNames[nameIndex] = currentNameInput;
            nameIndex = nameIndex + 1;
        }

        // Prompt user to enter scores
        System.out.print("Enter scores: ");

        // Read each student score into the scores array
        int scoreIndex = 0;
        while (scoreIndex < numberOfStudents) {
            int currentScoreInput = userInputScanner.nextInt();
            studentScores[scoreIndex] = currentScoreInput;
            scoreIndex = scoreIndex + 1;
        }

        // Sort the parallel arrays based on scores in ascending order
        // Using a simple bubble sort for clarity
        int outerIndex = 0;
        while (outerIndex < numberOfStudents) {
            int innerIndex = 0;
            while (innerIndex < numberOfStudents - 1) {
                int currentScore = studentScores[innerIndex];
                int nextScore = studentScores[innerIndex + 1];

                // If the current score is greater than the next score, swap them
                if (currentScore > nextScore) {
                    // Swap scores
                    int temporaryScoreHolder = studentScores[innerIndex];
                    studentScores[innerIndex] = studentScores[innerIndex + 1];
                    studentScores[innerIndex + 1] = temporaryScoreHolder;

                    // Swap corresponding names to keep arrays parallel
                    String temporaryNameHolder = studentNames[innerIndex];
                    studentNames[innerIndex] = studentNames[innerIndex + 1];
                    studentNames[innerIndex + 1] = temporaryNameHolder;
                }

                innerIndex = innerIndex + 1;
            }
            outerIndex = outerIndex + 1;
        }

        // After sorting in ascending order, the highest score is at the last index
        // We need to print the top student: name and score
        if (numberOfStudents > 0) {
            int lastIndex = numberOfStudents - 1;
            String topStudentName = studentNames[lastIndex];
            int topStudentScore = studentScores[lastIndex];

            // Print the result in the required format
            System.out.println("Top student: " + topStudentName + " (" + topStudentScore + ")");
        }

        // Close the scanner to be safe
        userInputScanner.close();
    }
}