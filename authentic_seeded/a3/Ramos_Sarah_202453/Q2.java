import java.util.Scanner;

public class Q2 {
    public static void main(String[] args) {
        // Create a Scanner to read user input from the console
        Scanner userInputScanner = new Scanner(System.in);

        // Ask the user to enter the number of students
        System.out.print("Enter number of students: ");
        int numberOfStudents = userInputScanner.nextInt();

        // Create an array to store the names of the students
        String[] studentNames = new String[numberOfStudents];

        // Create an array to store the scores of the students
        int[] studentScores = new int[numberOfStudents];

        // Prompt the user to enter all the names
        System.out.print("Enter names: ");
        // Read each name into the studentNames array
        for (int currentIndex = 0; currentIndex < numberOfStudents; currentIndex++) {
            studentNames[currentIndex] = userInputScanner.next();
        }

        // Prompt the user to enter all the scores
        System.out.print("Enter scores: ");
        // Read each score into the studentScores array
        for (int currentIndex = 0; currentIndex < numberOfStudents; currentIndex++) {
            studentScores[currentIndex] = userInputScanner.nextInt();
        }

        // Now we will sort the arrays in ascending order based on the scores
        // We use a simple bubble sort with parallel swapping of names and scores
        for (int outerIndex = 0; outerIndex < numberOfStudents - 1; outerIndex++) {
            for (int innerIndex = 0; innerIndex < numberOfStudents - 1 - outerIndex; innerIndex++) {

                // Declare math-like variables to compare scores
                int a = studentScores[innerIndex];
                int b = studentScores[innerIndex + 1];

                // If the current score is greater than the next score, we swap them
                if (a > b) {
                    // Swap scores
                    int temporaryScore = studentScores[innerIndex];
                    studentScores[innerIndex] = studentScores[innerIndex + 1];
                    studentScores[innerIndex + 1] = temporaryScore;

                    // Swap corresponding names to keep arrays parallel
                    String temporaryName = studentNames[innerIndex];
                    studentNames[innerIndex] = studentNames[innerIndex + 1];
                    studentNames[innerIndex + 1] = temporaryName;
                }
            }
        }

        // After sorting in ascending order, the student with the highest score
        // will be at the last index (numberOfStudents - 1)
        int highestScoreIndex = numberOfStudents - 1;

        // Extract the top student's name and score
        String topStudentName = studentNames[highestScoreIndex];
        int topStudentScore = studentScores[highestScoreIndex];

        // Print the result in the requested format
        System.out.println("Top student: " + topStudentName + " (" + topStudentScore + ")");

        // Close the scanner to free system resources
        userInputScanner.close();
    }
}