import java.util.Scanner;

public class Q2 {
    public static void main(String[] args) {
        // Create a Scanner object for reading user input from the console
        Scanner userInputScanner = new Scanner(System.in);

        // Prompt for number of students
        System.out.print("Enter number of students: ");

        // Read the number of students
        int numberOfStudents = userInputScanner.nextInt();

        // Extra cautious check for non-positive number of students
        if (numberOfStudents < 0) {
            // If number is negative, set to zero to avoid problems
            numberOfStudents = 0;
        }

        // Create parallel arrays for names and scores using the number of students
        String[] studentNames = new String[numberOfStudents];
        int[] studentScores = new int[numberOfStudents];

        // Prompt user to enter names
        System.out.print("Enter names: ");

        // Read all names into the studentNames array
        int nameIndex = 0;
        while (nameIndex < numberOfStudents) {
            // Read the next name token
            String currentNameInput = userInputScanner.next();
            // Store the name in the array
            studentNames[nameIndex] = currentNameInput;
            nameIndex = nameIndex + 1;
        }

        // Prompt user to enter scores
        System.out.print("Enter scores: ");

        // Read all scores into the studentScores array
        int scoreIndex = 0;
        while (scoreIndex < numberOfStudents) {
            // Read the next score
            int currentScoreInput = userInputScanner.nextInt();
            // Store the score in the array
            studentScores[scoreIndex] = currentScoreInput;
            scoreIndex = scoreIndex + 1;
        }

        // Sort the parallel arrays by scores in ascending order
        // Using a simple bubble sort to keep logic straightforward
        int outerLoopIndex = 0;
        while (outerLoopIndex < numberOfStudents) {
            int innerLoopIndex = 0;
            while (innerLoopIndex < numberOfStudents - 1) {
                // Get current score and next score for comparison
                int currentScoreValue = studentScores[innerLoopIndex];
                int nextScoreValue = studentScores[innerLoopIndex + 1];

                // If current score is greater than next score, swap them
                if (currentScoreValue > nextScoreValue) {
                    // Swap scores using a temporary holder variable
                    int temporaryScoreHolder = studentScores[innerLoopIndex];
                    studentScores[innerLoopIndex] = studentScores[innerLoopIndex + 1];
                    studentScores[innerLoopIndex + 1] = temporaryScoreHolder;

                    // Swap corresponding names to keep arrays parallel
                    String temporaryNameHolder = studentNames[innerLoopIndex];
                    studentNames[innerLoopIndex] = studentNames[innerLoopIndex + 1];
                    studentNames[innerLoopIndex + 1] = temporaryNameHolder;
                }

                innerLoopIndex = innerLoopIndex + 1;
            }
            outerLoopIndex = outerLoopIndex + 1;
        }

        // After sorting in ascending order, the highest score is at the last index
        // Edge case: if numberOfStudents is zero, we should avoid accessing the arrays
        if (numberOfStudents > 0) {
            int topStudentIndex = numberOfStudents - 1;

            // Retrieve top student's name and score using temporary variables
            String topStudentName = studentNames[topStudentIndex];
            int topStudentScore = studentScores[topStudentIndex];

            // Print the top student's information in the required format
            System.out.println("Top student: " + topStudentName + " (" + topStudentScore + ")");
        }

        // Close the scanner to be extra safe with resources
        userInputScanner.close();
    }
}