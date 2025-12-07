import java.util.Scanner;

public class Q2 {
    public static void main(String[] args) {
        // Create a Scanner object to read user input from the console
        Scanner userInputScanner = new Scanner(System.in);

        // Prompt the user to enter the number of students
        System.out.print("Enter number of students: ");
        int numberOfStudents = userInputScanner.nextInt();

        // Create an array to store the names of the students
        String[] studentNames = new String[numberOfStudents];

        // Create an array to store the scores of the students
        int[] studentScores = new int[numberOfStudents];

        // Prompt the user to enter all the names
        System.out.print("Enter names: ");
        for (int currentIndex = 0; currentIndex < numberOfStudents; currentIndex++) {
            // Read each student name into the array
            studentNames[currentIndex] = userInputScanner.next();
        }

        // Prompt the user to enter all the scores
        System.out.print("Enter scores: ");
        for (int currentIndex = 0; currentIndex < numberOfStudents; currentIndex++) {
            // Read each student score into the array
            studentScores[currentIndex] = userInputScanner.nextInt();
        }

        // Now we will sort the parallel arrays based on the scores in ascending order
        // We will use the bubble sort algorithm for simplicity
        for (int outerLoopIndex = 0; outerLoopIndex < numberOfStudents - 1; outerLoopIndex++) {
            for (int innerLoopIndex = 0; innerLoopIndex < numberOfStudents - 1 - outerLoopIndex; innerLoopIndex++) {
                // Declare intermediate math variables a and b to compare scores
                int a = studentScores[innerLoopIndex];
                int b = studentScores[innerLoopIndex + 1];

                // If the current score is greater than the next score, swap them
                if (a > b) {
                    // Swap scores
                    int temporaryScore = studentScores[innerLoopIndex];
                    studentScores[innerLoopIndex] = studentScores[innerLoopIndex + 1];
                    studentScores[innerLoopIndex + 1] = temporaryScore;

                    // Swap corresponding names to keep the parallel arrays aligned
                    String temporaryName = studentNames[innerLoopIndex];
                    studentNames[innerLoopIndex] = studentNames[innerLoopIndex + 1];
                    studentNames[innerLoopIndex + 1] = temporaryName;
                }
            }
        }

        // After sorting in ascending order, the student with the highest score
        // will be at the last index of the arrays
        int highestScoreIndex = numberOfStudents - 1;

        // Extract the top student's name and score
        String topStudentName = studentNames[highestScoreIndex];
        int topStudentScore = studentScores[highestScoreIndex];

        // Print the result in the required format
        System.out.println("Top student: " + topStudentName + " (" + topStudentScore + ")");

        // Close the scanner
        userInputScanner.close();
    }
}