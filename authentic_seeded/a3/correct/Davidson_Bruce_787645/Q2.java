import java.util.Scanner;

public class Q2 {
    public static void main(String[] args) {
        // Create a Scanner to read input from the user
        Scanner userInputScanner = new Scanner(System.in);

        // Prompt the user to enter the number of students
        System.out.print("Enter number of students: ");
        int numberOfStudents = userInputScanner.nextInt();

        // Extra check: ensure number of students is not negative
        if (numberOfStudents < 0) {
            numberOfStudents = 0;
        }

        // Create the parallel arrays for names and scores
        String[] studentNames = new String[numberOfStudents];
        int[] studentScores = new int[numberOfStudents];

        // Prompt the user to enter the names
        System.out.print("Enter names: ");
        // Read each name into the names array
        for (int currentIndex = 0; currentIndex < numberOfStudents; currentIndex++) {
            String currentNameInput = userInputScanner.next();
            studentNames[currentIndex] = currentNameInput;
        }

        // Prompt the user to enter the scores
        System.out.print("Enter scores: ");
        // Read each score into the scores array
        for (int currentIndex = 0; currentIndex < numberOfStudents; currentIndex++) {
            int currentScoreInput = userInputScanner.nextInt();
            studentScores[currentIndex] = currentScoreInput;
        }

        // Sort the arrays in ascending order based on scores using a simple bubble sort
        // We will swap both the score and the corresponding name to keep them aligned
        for (int outerIndex = 0; outerIndex < numberOfStudents - 1; outerIndex++) {
            // Each pass will push the largest score towards the end
            for (int innerIndex = 0; innerIndex < numberOfStudents - 1 - outerIndex; innerIndex++) {
                int currentScore = studentScores[innerIndex];
                int nextScore = studentScores[innerIndex + 1];

                // If the current score is greater than the next score, we swap them
                if (currentScore > nextScore) {
                    // Swap scores using a temporary holder
                    int temporaryScoreHolder = currentScore;
                    studentScores[innerIndex] = nextScore;
                    studentScores[innerIndex + 1] = temporaryScoreHolder;

                    // Also swap the corresponding names using a temporary holder
                    String temporaryNameHolder = studentNames[innerIndex];
                    studentNames[innerIndex] = studentNames[innerIndex + 1];
                    studentNames[innerIndex + 1] = temporaryNameHolder;
                }
            }
        }

        // After sorting, the highest score should be at the last position if there is at least one student
        if (numberOfStudents > 0) {
            int lastIndex = numberOfStudents - 1;
            String topStudentName = studentNames[lastIndex];
            int topStudentScore = studentScores[lastIndex];

            // Print the top student's name and score in the required format
            System.out.println("Top student: " + topStudentName + " (" + topStudentScore + ")");
        }

        // Close the scanner as a good practice
        userInputScanner.close();
    }
}