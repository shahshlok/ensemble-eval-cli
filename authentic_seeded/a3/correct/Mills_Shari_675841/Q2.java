import java.util.Scanner;

public class Q2 {
    public static void main(String[] args) {
        // Step 1: Create a Scanner to read input from the user
        Scanner userInputScanner = new Scanner(System.in);

        // Step 2: Ask the user to enter the number of students
        System.out.print("Enter number of students: ");
        int numberOfStudents = userInputScanner.nextInt();

        // Step 3: Create an array to store the student names
        String[] studentNames = new String[numberOfStudents];

        // Step 4: Create an array to store the student scores
        int[] studentScores = new int[numberOfStudents];

        // Step 5: Ask the user to enter all the names
        System.out.print("Enter names: ");
        for (int currentIndex = 0; currentIndex < numberOfStudents; currentIndex++) {
            // Step 6: Read each name into the names array
            studentNames[currentIndex] = userInputScanner.next();
        }

        // Step 7: Ask the user to enter all the scores
        System.out.print("Enter scores: ");
        for (int currentIndex = 0; currentIndex < numberOfStudents; currentIndex++) {
            // Step 8: Read each score into the scores array
            studentScores[currentIndex] = userInputScanner.nextInt();
        }

        // Step 9: Sort the students by their scores in ascending order using a simple bubble sort
        // We need to keep names and scores matched, so we swap in both arrays together
        for (int outerIndex = 0; outerIndex < numberOfStudents - 1; outerIndex++) {
            for (int innerIndex = 0; innerIndex < numberOfStudents - 1 - outerIndex; innerIndex++) {
                // Step 10: If the current score is greater than the next score, swap them
                if (studentScores[innerIndex] > studentScores[innerIndex + 1]) {
                    // Swap scores
                    int temporaryScore = studentScores[innerIndex];
                    studentScores[innerIndex] = studentScores[innerIndex + 1];
                    studentScores[innerIndex + 1] = temporaryScore;

                    // Swap corresponding names so they stay parallel to scores
                    String temporaryName = studentNames[innerIndex];
                    studentNames[innerIndex] = studentNames[innerIndex + 1];
                    studentNames[innerIndex + 1] = temporaryName;
                }
            }
        }

        // Step 11: After sorting in ascending order, the last element has the highest score
        String topStudentName = studentNames[numberOfStudents - 1];
        int topStudentScore = studentScores[numberOfStudents - 1];

        // Step 12: Print the top student's name and score in the required format
        System.out.println("Top student: " + topStudentName + " (" + topStudentScore + ")");

        // Step 13: Close the scanner
        userInputScanner.close();
    }
}