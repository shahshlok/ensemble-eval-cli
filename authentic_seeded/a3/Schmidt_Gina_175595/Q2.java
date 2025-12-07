import java.util.Scanner;

public class Q2 {
    public static void main(String[] args) {
        // Step 1: Create a Scanner to read input from the user
        Scanner userInputScanner = new Scanner(System.in);

        // Step 2: Ask the user for the number of students and read it
        System.out.print("Enter number of students: ");
        int numberOfStudents = userInputScanner.nextInt();

        // Step 3: Create an array to store the names of the students
        String[] studentNames = new String[numberOfStudents];

        // Step 4: Create an array to store the scores of the students
        int[] studentScores = new int[numberOfStudents];

        // Step 5: Read all the student names into the names array
        System.out.print("Enter names: ");
        for (int currentIndex = 0; currentIndex < numberOfStudents; currentIndex++) {
            studentNames[currentIndex] = userInputScanner.next();
        }

        // Step 6: Read all the student scores into the scores array
        System.out.print("Enter scores: ");
        for (int currentIndex = 0; currentIndex < numberOfStudents; currentIndex++) {
            studentScores[currentIndex] = userInputScanner.nextInt();
        }

        // Step 7: Sort both arrays using the scores array in ascending order
        // We will use a simple bubble sort to keep the names and scores in sync
        for (int outerIndex = 0; outerIndex < numberOfStudents - 1; outerIndex++) {
            for (int innerIndex = 0; innerIndex < numberOfStudents - 1 - outerIndex; innerIndex++) {
                // If the current score is greater than the next score, swap them
                if (studentScores[innerIndex] > studentScores[innerIndex + 1]) {
                    // Swap scores
                    int temporaryScore = studentScores[innerIndex];
                    studentScores[innerIndex] = studentScores[innerIndex + 1];
                    studentScores[innerIndex + 1] = temporaryScore;

                    // Swap corresponding names so they stay matched with scores
                    String temporaryName = studentNames[innerIndex];
                    studentNames[innerIndex] = studentNames[innerIndex + 1];
                    studentNames[innerIndex + 1] = temporaryName;
                }
            }
        }

        // Step 8: After sorting ascending, the highest score is at the last index
        int indexOfTopStudent = numberOfStudents - 1;
        String topStudentName = studentNames[indexOfTopStudent];
        int topStudentScore = studentScores[indexOfTopStudent];

        // Step 9: Print the top student's name and score in the required format
        System.out.println("Top student: " + topStudentName + " (" + topStudentScore + ")");

        // Step 10: Close the scanner
        userInputScanner.close();
    }
}