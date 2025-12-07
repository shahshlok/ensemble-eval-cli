import java.util.Scanner;

public class Q2 {
    public static void main(String[] args) {
        // Create a Scanner object to read user input from the keyboard
        Scanner userInputScanner = new Scanner(System.in);

        // Ask the user to enter the number of students
        System.out.print("Enter number of students: ");
        int numberOfStudents = userInputScanner.nextInt();

        // Create an array to store the names of the students
        String[] studentNamesArray = new String[numberOfStudents];

        // Create an array to store the scores of the students
        int[] studentScoresArray = new int[numberOfStudents];

        // Ask the user to enter the names
        System.out.print("Enter names: ");

        // Read each name into the studentNamesArray
        for (int index = 0; index < numberOfStudents; index++) {
            studentNamesArray[index] = userInputScanner.next();
        }

        // Ask the user to enter the scores
        System.out.print("Enter scores: ");

        // Read each score into the studentScoresArray
        for (int index = 0; index < numberOfStudents; index++) {
            studentScoresArray[index] = userInputScanner.nextInt();
        }

        // Now we need to sort the students by score in ascending order
        // We will use a simple bubble sort that keeps the parallel arrays in sync

        // Outer loop: number of passes
        for (int passNumber = 0; passNumber < numberOfStudents - 1; passNumber++) {
            // Inner loop: compare adjacent elements
            for (int index = 0; index < numberOfStudents - 1 - passNumber; index++) {
                // Declare intermediate math variables for readability
                int currentScore = studentScoresArray[index];
                int nextScore = studentScoresArray[index + 1];

                // If the current score is greater than the next score, we swap
                if (currentScore > nextScore) {
                    // Swap scores
                    int temporaryScore = studentScoresArray[index];
                    studentScoresArray[index] = studentScoresArray[index + 1];
                    studentScoresArray[index + 1] = temporaryScore;

                    // Swap corresponding names to keep parallel arrays aligned
                    String temporaryName = studentNamesArray[index];
                    studentNamesArray[index] = studentNamesArray[index + 1];
                    studentNamesArray[index + 1] = temporaryName;
                }
            }
        }

        // After sorting in ascending order, the student with the highest score
        // will be at the last index (numberOfStudents - 1)

        int highestStudentIndex = numberOfStudents - 1;
        String topStudentName = studentNamesArray[highestStudentIndex];
        int topStudentScore = studentScoresArray[highestStudentIndex];

        // Print the top student in the required format
        System.out.println("Top student: " + topStudentName + " (" + topStudentScore + ")");

        // Close the scanner to avoid resource leak
        userInputScanner.close();
    }
}