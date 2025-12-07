import java.util.Scanner;

public class Q2 {
    public static void main(String[] args) {

        // Create a Scanner to read user input
        Scanner userInputScanner = new Scanner(System.in);

        // Prompt the user to enter the number of students
        System.out.print("Enter number of students: ");

        // Read the number of students
        int numberOfStudents = userInputScanner.nextInt();

        // Make sure the number of students is non-negative (nervous about edge cases)
        if (numberOfStudents < 0) {
            numberOfStudents = 0;
        }

        // Create arrays to store names and scores, even if numberOfStudents is 0
        String[] studentNames = new String[numberOfStudents];
        int[] studentScores = new int[numberOfStudents];

        // If there is at least one student, proceed to read names and scores
        if (numberOfStudents > 0) {

            // Prompt the user to enter the names
            System.out.print("Enter names: ");

            // Read each student name into the studentNames array
            for (int index = 0; index < numberOfStudents; index++) {
                // Read next name token
                String currentNameInput = userInputScanner.next();
                studentNames[index] = currentNameInput;
            }

            // Prompt the user to enter the scores
            System.out.print("Enter scores: ");

            // Read each student score into the studentScores array
            for (int index = 0; index < numberOfStudents; index++) {
                int currentScoreInput = userInputScanner.nextInt();
                studentScores[index] = currentScoreInput;
            }

            // Sort both arrays based on scores using a simple bubble sort
            // This keeps the arrays in parallel so that names and scores stay matched
            boolean didSwapOccur = true;

            // Perform bubble sort while swaps are happening
            while (didSwapOccur) {

                // Assume no swap at the start of each pass
                didSwapOccur = false;

                // Go through the arrays and compare scores
                for (int index = 0; index < numberOfStudents - 1; index++) {

                    // Store current and next scores into temporary variables
                    int currentScoreValue = studentScores[index];
                    int nextScoreValue = studentScores[index + 1];

                    // If the current score is greater than the next score, we need to swap
                    if (currentScoreValue > nextScoreValue) {

                        // Swap scores
                        int temporaryScoreHolder = studentScores[index];
                        studentScores[index] = studentScores[index + 1];
                        studentScores[index + 1] = temporaryScoreHolder;

                        // Swap names in the same way to keep data parallel
                        String temporaryNameHolder = studentNames[index];
                        studentNames[index] = studentNames[index + 1];
                        studentNames[index + 1] = temporaryNameHolder;

                        // Mark that a swap occurred, so we need another pass
                        didSwapOccur = true;
                    }
                }
            }

            // After sorting in ascending order, the highest score will be at the last index
            int lastIndex = numberOfStudents - 1;

            // Just to be safe, check that lastIndex is at least 0
            if (lastIndex >= 0) {

                String topStudentName = studentNames[lastIndex];
                int topStudentScore = studentScores[lastIndex];

                // Print the top student information
                System.out.println("Top student: " + topStudentName + " (" + topStudentScore + ")");
            }
        }

        // Close the scanner to be polite, even though it is not strictly required
        userInputScanner.close();
    }
}