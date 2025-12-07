import java.util.Scanner;

public class Q2 {
    public static void main(String[] args) {
        // Create a Scanner object for user input
        Scanner userInputScanner = new Scanner(System.in);

        // Ask the user for the number of students
        System.out.print("Enter number of students: ");
        int numberOfStudents = userInputScanner.nextInt();

        // Make sure the number of students is not negative
        if (numberOfStudents < 0) {
            numberOfStudents = 0;
        }

        // Create parallel arrays for names and scores
        String[] studentNames = new String[numberOfStudents];
        int[] studentScores = new int[numberOfStudents];

        // Only proceed to read names and scores if there is at least one student
        if (numberOfStudents > 0) {
            // Prompt user to enter names
            System.out.print("Enter names: ");
            // Read each name and store in the names array
            for (int currentIndex = 0; currentIndex < numberOfStudents; currentIndex++) {
                String currentNameInput = userInputScanner.next();
                studentNames[currentIndex] = currentNameInput;
            }

            // Prompt user to enter scores
            System.out.print("Enter scores: ");
            // Read each score and store in the scores array
            for (int currentIndex = 0; currentIndex < numberOfStudents; currentIndex++) {
                int currentScoreInput = userInputScanner.nextInt();
                studentScores[currentIndex] = currentScoreInput;
            }

            // Sort the parallel arrays by scores in ascending order
            // Using a simple bubble sort to keep names and scores aligned
            for (int outerIndex = 0; outerIndex < numberOfStudents - 1; outerIndex++) {
                for (int innerIndex = 0; innerIndex < numberOfStudents - 1 - outerIndex; innerIndex++) {
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
                }
            }

            // After sorting in ascending order, the highest score is at the last index
            int indexOfTopStudent = numberOfStudents - 1;

            // Extra checks to be safe
            if (indexOfTopStudent >= 0 && indexOfTopStudent < numberOfStudents) {
                String topStudentName = studentNames[indexOfTopStudent];
                int topStudentScore = studentScores[indexOfTopStudent];

                // Print the top student information
                System.out.println("Top student: " + topStudentName + " (" + topStudentScore + ")");
            }
        }

        // Close the scanner
        userInputScanner.close();
    }
}