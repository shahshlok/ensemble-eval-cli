import java.util.Scanner;
import java.util.Arrays;

public class Q2 {
    public static void main(String[] args) {
        // Create a Scanner to read user input from the console
        Scanner keyboardInput = new Scanner(System.in);

        // Prompt the user for the number of students
        System.out.print("Enter number of students: ");
        int numberOfStudents = keyboardInput.nextInt();

        // Extra nervous check: ensure number of students is not negative
        if (numberOfStudents < 0) {
            numberOfStudents = 0;
        }

        // Create an array to store the student names
        String[] studentNames = new String[numberOfStudents];

        // Create an array to store the student scores
        int[] studentScores = new int[numberOfStudents];

        // Prompt the user to enter all the names
        System.out.print("Enter names: ");

        // Read each name into the studentNames array
        for (int index = 0; index < numberOfStudents; index++) {
            // Read next name as a String token
            String inputName = keyboardInput.next();
            // Store the read name into the array
            studentNames[index] = inputName;
        }

        // Prompt the user to enter all the scores
        System.out.print("Enter scores: ");

        // Read each score into the studentScores array
        for (int index = 0; index < numberOfStudents; index++) {
            // Read next score as an integer
            int inputScore = keyboardInput.nextInt();
            // Extra nervous check: no specific constraints given, but we still store it
            studentScores[index] = inputScore;
        }

        // Sort the data in ascending order based on scores
        // I will use Arrays.sort because it should keep everything consistent
        if (numberOfStudents > 1) {
            int[] temporaryScoresHolder = studentScores;
            Arrays.sort(temporaryScoresHolder);
            studentScores = temporaryScoresHolder;
        }

        // After sorting in ascending order, the student with the highest score
        // will be at the last index (numberOfStudents - 1), if there is at least one student
        if (numberOfStudents > 0) {
            int highestScoreIndex = numberOfStudents - 1;

            String topStudentName = studentNames[highestScoreIndex];
            int topStudentScore = studentScores[highestScoreIndex];

            // Nervous check: ensure name is not null just in case
            if (topStudentName == null) {
                topStudentName = "";
            }

            // Print the top student's name and score in the required format
            System.out.println("Top student: " + topStudentName + " (" + topStudentScore + ")");
        }

        // Close the scanner to avoid resource leaks
        keyboardInput.close();
    }
}