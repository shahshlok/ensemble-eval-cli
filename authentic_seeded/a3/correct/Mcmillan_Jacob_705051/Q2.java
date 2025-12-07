import java.util.Scanner;

public class Q2 {
    public static void main(String[] args) {
        // Create a Scanner object to read user input from the keyboard
        Scanner userInputScanner = new Scanner(System.in);

        // Step 1: Ask the user for the number of students and read it
        System.out.print("Enter number of students: ");
        int numberOfStudents = userInputScanner.nextInt();

        // Step 2: Create an array to store the names of the students
        String[] studentNames = new String[numberOfStudents];

        // Step 3: Create an array to store the scores of the students
        int[] studentScores = new int[numberOfStudents];

        // Step 4: Read the student names
        System.out.print("Enter names: ");
        for (int index = 0; index < numberOfStudents; index++) {
            // Read each name as a String
            studentNames[index] = userInputScanner.next();
        }

        // Step 5: Read the student scores
        System.out.print("Enter scores: ");
        for (int index = 0; index < numberOfStudents; index++) {
            // Read each score as an integer
            studentScores[index] = userInputScanner.nextInt();
        }

        // Step 6: Sort the parallel arrays based on scores in ascending order
        // We will use a simple bubble sort to keep the code easy to follow
        for (int outerIndex = 0; outerIndex < numberOfStudents - 1; outerIndex++) {
            // Compare each pair of adjacent elements
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

        // Step 7: After sorting in ascending order, the last element has the highest score
        int indexOfTopStudent = numberOfStudents - 1;
        String topStudentName = studentNames[indexOfTopStudent];
        int topStudentScore = studentScores[indexOfTopStudent];

        // Step 8: Print the name and score of the top student in the required format
        System.out.println("Top student: " + topStudentName + " (" + topStudentScore + ")");

        // Step 9: Close the scanner to free resources
        userInputScanner.close();
    }
}