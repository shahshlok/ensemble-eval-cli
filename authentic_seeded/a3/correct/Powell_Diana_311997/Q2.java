import java.util.Scanner;

public class Q2 {
    public static void main(String[] args) {
        // Create a Scanner object to read user input from the console
        Scanner keyboardScanner = new Scanner(System.in);

        // Prompt the user to enter the number of students
        System.out.print("Enter number of students: ");
        int numberOfStudents = keyboardScanner.nextInt();

        // Just in case, check if the number of students is greater than 0
        if (numberOfStudents <= 0) {
            // If there are no students or a negative number, we cannot proceed safely
            // We will not attempt to read any names or scores
            return;
        }

        // Create an array to store the names of the students
        String[] studentNames = new String[numberOfStudents];

        // Create an array to store the scores of the students
        int[] studentScores = new int[numberOfStudents];

        // Prompt the user to enter all the student names in one line
        System.out.print("Enter names: ");

        // Read each student name into the studentNames array
        for (int index = 0; index < numberOfStudents; index++) {
            String currentNameInput = keyboardScanner.next();
            studentNames[index] = currentNameInput;
        }

        // Prompt the user to enter all the student scores in one line
        System.out.print("Enter scores: ");

        // Read each student score into the studentScores array
        for (int index = 0; index < numberOfStudents; index++) {
            int currentScoreInput = keyboardScanner.nextInt();
            studentScores[index] = currentScoreInput;
        }

        // Now we need to sort both arrays in ascending order based on the scores
        // We will use a simple bubble sort because it is easier to understand
        // Bubble sort: repeatedly step through the list, compare adjacent elements and swap them if needed
        boolean didSwapHappen = true;

        // We keep looping as long as at least one swap happens in a pass
        while (didSwapHappen) {
            // At the start of each pass, we assume no swaps will happen
            didSwapHappen = false;

            // Go through the arrays and compare adjacent scores
            for (int index = 0; index < numberOfStudents - 1; index++) {
                int currentScore = studentScores[index];
                int nextScore = studentScores[index + 1];

                // If the current score is greater than the next score, we need to swap
                if (currentScore > nextScore) {
                    // Swap the scores
                    int temporaryScoreHolder = studentScores[index];
                    studentScores[index] = studentScores[index + 1];
                    studentScores[index + 1] = temporaryScoreHolder;

                    // Swap the corresponding names so that parallel arrays stay aligned
                    String temporaryNameHolder = studentNames[index];
                    studentNames[index] = studentNames[index + 1];
                    studentNames[index + 1] = temporaryNameHolder;

                    // Since we swapped, we mark that a swap happened in this pass
                    didSwapHappen = true;
                }
            }
        }

        // After sorting in ascending order, the highest score will be at the last index
        int lastIndex = numberOfStudents - 1;

        // Just in case, check that lastIndex is within the array bounds
        if (lastIndex >= 0 && lastIndex < numberOfStudents) {
            String topStudentName = studentNames[lastIndex];
            int topStudentScore = studentScores[lastIndex];

            // Print the top student's name and score in the required format
            System.out.println("Top student: " + topStudentName + " (" + topStudentScore + ")");
        }

        // Close the scanner to avoid resource leaks
        keyboardScanner.close();
    }
}