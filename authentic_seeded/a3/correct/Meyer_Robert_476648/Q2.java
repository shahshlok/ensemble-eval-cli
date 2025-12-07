import java.util.Scanner;

public class Q2 {
    public static void main(String[] args) {
        // Create a Scanner object to read user input from the console
        Scanner userInputScanner = new Scanner(System.in);

        // Ask the user to enter the number of students
        System.out.print("Enter number of students: ");

        // Read the number of students and store it in a descriptive variable
        int numberOfStudents = userInputScanner.nextInt();

        // Create a String array to store the names of the students
        String[] studentNamesArray = new String[numberOfStudents];

        // Create an int array to store the scores of the students
        int[] studentScoresArray = new int[numberOfStudents];

        // Prompt the user to enter all the names
        System.out.print("Enter names: ");

        // Read each name into the studentNamesArray
        for (int currentIndex = 0; currentIndex < numberOfStudents; currentIndex++) {
            studentNamesArray[currentIndex] = userInputScanner.next();
        }

        // Prompt the user to enter all the scores
        System.out.print("Enter scores: ");

        // Read each score into the studentScoresArray
        for (int currentIndex = 0; currentIndex < numberOfStudents; currentIndex++) {
            studentScoresArray[currentIndex] = userInputScanner.nextInt();
        }

        // Now we will sort the parallel arrays by scores in ascending order
        // We use a simple bubble sort that keeps names and scores aligned
        for (int outerIndex = 0; outerIndex < numberOfStudents - 1; outerIndex++) {
            for (int innerIndex = 0; innerIndex < numberOfStudents - 1 - outerIndex; innerIndex++) {
                // Declare intermediate math variables for comparison
                int a = studentScoresArray[innerIndex];
                int b = studentScoresArray[innerIndex + 1];
                int difference = a - b;

                // If the current score is greater than the next score, swap them
                if (difference > 0) {
                    // Swap scores
                    int temporaryScore = studentScoresArray[innerIndex];
                    studentScoresArray[innerIndex] = studentScoresArray[innerIndex + 1];
                    studentScoresArray[innerIndex + 1] = temporaryScore;

                    // Swap corresponding names to keep arrays parallel
                    String temporaryName = studentNamesArray[innerIndex];
                    studentNamesArray[innerIndex] = studentNamesArray[innerIndex + 1];
                    studentNamesArray[innerIndex + 1] = temporaryName;
                }
            }
        }

        // After sorting in ascending order, the highest score is at the last index
        int highestScoreIndex = numberOfStudents - 1;

        // Extract the top student's name and score
        String topStudentName = studentNamesArray[highestScoreIndex];
        int topStudentScore = studentScoresArray[highestScoreIndex];

        // Print the result in the requested format
        System.out.println("Top student: " + topStudentName + " (" + topStudentScore + ")");

        // Close the scanner to avoid resource leaks
        userInputScanner.close();
    }
}