import java.util.Scanner;

public class Q2 {
    public static void main(String[] args) {
        // Create a scanner to read user input from the console
        Scanner userInputScanner = new Scanner(System.in);

        // Prompt the user to enter the number of students
        System.out.print("Enter number of students: ");
        int numberOfStudents = userInputScanner.nextInt();

        // Make sure the number of students is not negative
        if (numberOfStudents < 0) {
            numberOfStudents = 0;
        }

        // Create arrays for student names and scores
        String[] studentNames = new String[numberOfStudents];
        int[] studentScores = new int[numberOfStudents];

        // Prompt the user to enter the names
        System.out.print("Enter names: ");

        // Read each name into the names array
        for (int index = 0; index < numberOfStudents; index++) {
            String currentNameInput = userInputScanner.next();
            studentNames[index] = currentNameInput;
        }

        // Prompt the user to enter the scores
        System.out.print("Enter scores: ");

        // Read each score into the scores array
        for (int index = 0; index < numberOfStudents; index++) {
            int currentScoreInput = userInputScanner.nextInt();
            studentScores[index] = currentScoreInput;
        }

        // Sort the arrays in ascending order based on the scores array using selection sort
        int outerLoopIndex = 0;
        while (outerLoopIndex < numberOfStudents) {
            // Assume the current index has the minimum score
            int currentMinimumIndex = outerLoopIndex;

            int innerLoopIndex = outerLoopIndex + 1;
            while (innerLoopIndex < numberOfStudents) {
                // If a smaller score is found, update the minimum index
                if (studentScores[innerLoopIndex] < studentScores[currentMinimumIndex]) {
                    currentMinimumIndex = innerLoopIndex;
                }
                innerLoopIndex = innerLoopIndex + 1;
            }

            // Swap only if the minimum index is different from the current index
            if (currentMinimumIndex != outerLoopIndex) {
                // Swap scores
                int temporaryScoreHolder = studentScores[outerLoopIndex];
                studentScores[outerLoopIndex] = studentScores[currentMinimumIndex];
                studentScores[currentMinimumIndex] = temporaryScoreHolder;

                // Swap names to keep arrays parallel
                String temporaryNameHolder = studentNames[outerLoopIndex];
                studentNames[outerLoopIndex] = studentNames[currentMinimumIndex];
                studentNames[currentMinimumIndex] = temporaryNameHolder;
            }

            outerLoopIndex = outerLoopIndex + 1;
        }

        // After sorting, the student with the highest score will be at the last index
        // Check that there is at least one student before trying to access the arrays
        if (numberOfStudents > 0) {
            int lastIndex = numberOfStudents - 1;
            String topStudentName = studentNames[lastIndex];
            int topStudentScore = studentScores[lastIndex];

            // Print the top student's name and score
            System.out.println("Top student: " + topStudentName + " (" + topStudentScore + ")");
        }

        // Close the scanner to avoid resource leaks
        userInputScanner.close();
    }
}