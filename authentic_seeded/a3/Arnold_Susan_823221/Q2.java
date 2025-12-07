import java.util.Scanner;

public class Q2 {
    public static void main(String[] args) {
        // Create a Scanner so we can read input from the user
        Scanner userInputScanner = new Scanner(System.in);

        // Step 1: Ask for the number of students and read it
        System.out.print("Enter number of students: ");
        int numberOfStudents = userInputScanner.nextInt();

        // Step 2: Create an array to store the student names
        String[] studentNames = new String[numberOfStudents];

        // Step 3: Create an array to store the student scores
        int[] studentScores = new int[numberOfStudents];

        // Step 4: Read all the names from the user
        System.out.print("Enter names: ");
        for (int index = 0; index < numberOfStudents; index++) {
            // Read each name and store it in the names array
            studentNames[index] = userInputScanner.next();
        }

        // Step 5: Read all the scores from the user
        System.out.print("Enter scores: ");
        for (int index = 0; index < numberOfStudents; index++) {
            // Read each score and store it in the scores array
            studentScores[index] = userInputScanner.nextInt();
        }

        // Step 6: Sort the students by score in ascending order
        // We will use a simple bubble sort on the parallel arrays
        for (int outerIndex = 0; outerIndex < numberOfStudents - 1; outerIndex++) {
            for (int innerIndex = 0; innerIndex < numberOfStudents - 1 - outerIndex; innerIndex++) {
                // If the current score is greater than the next score, we swap them
                if (studentScores[innerIndex] > studentScores[innerIndex + 1]) {
                    // Swap scores
                    int temporaryScore = studentScores[innerIndex];
                    studentScores[innerIndex] = studentScores[innerIndex + 1];
                    studentScores[innerIndex + 1] = temporaryScore;

                    // Swap corresponding names so the arrays stay parallel
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

        // Step 8: Print the top student's name and score in the required format
        System.out.println("Top student: " + topStudentName + " (" + topStudentScore + ")");

        // Step 9: Close the Scanner
        userInputScanner.close();
    }
}