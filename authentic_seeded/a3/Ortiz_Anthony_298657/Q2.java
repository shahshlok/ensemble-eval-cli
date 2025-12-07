import java.util.Scanner;

public class Q2 {
    public static void main(String[] args) {
        // Create a Scanner to read user input from the keyboard
        Scanner userInputScanner = new Scanner(System.in);

        // Prompt the user to enter the number of students
        System.out.print("Enter number of students: ");
        int numberOfStudents = userInputScanner.nextInt();

        // Create an array to store the names of the students
        String[] studentNames = new String[numberOfStudents];

        // Create an array to store the scores of the students
        int[] studentScores = new int[numberOfStudents];

        // Prompt the user to enter the names
        System.out.print("Enter names: ");

        // Read each name into the studentNames array
        for (int index = 0; index < numberOfStudents; index++) {
            studentNames[index] = userInputScanner.next();
        }

        // Prompt the user to enter the scores
        System.out.print("Enter scores: ");

        // Read each score into the studentScores array
        for (int index = 0; index < numberOfStudents; index++) {
            studentScores[index] = userInputScanner.nextInt();
        }

        // Now we will sort both arrays based on the scores in ascending order
        // We will use a simple bubble sort to keep the logic easy to follow
        for (int outerIndex = 0; outerIndex < numberOfStudents - 1; outerIndex++) {
            // Compare each pair and swap if they are out of order
            for (int innerIndex = 0; innerIndex < numberOfStudents - 1 - outerIndex; innerIndex++) {
                // If the current score is greater than the next score, swap them
                if (studentScores[innerIndex] > studentScores[innerIndex + 1]) {
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

        // After sorting in ascending order, the highest score will be at the last index
        int indexOfTopStudent = numberOfStudents - 1;
        String topStudentName = studentNames[indexOfTopStudent];
        int topStudentScore = studentScores[indexOfTopStudent];

        // Print the top student's name and score in the required format
        System.out.println("Top student: " + topStudentName + " (" + topStudentScore + ")");

        // Close the scanner to free system resources
        userInputScanner.close();
    }
}