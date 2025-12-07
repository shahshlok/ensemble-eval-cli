import java.util.Scanner;

public class Q2 {
    public static void main(String[] args) {
        // Create a Scanner to read user input from the console
        Scanner userInputScanner = new Scanner(System.in);

        // Prompt the user to enter the number of students
        System.out.print("Enter number of students: ");
        int numberOfStudents = userInputScanner.nextInt();

        // Extra safety check to ensure the number of students is positive
        if (numberOfStudents < 0) {
            numberOfStudents = 0;
        }

        // Create an array to store the names of the students
        String[] studentNamesArray = new String[numberOfStudents];

        // Create an array to store the scores of the students
        int[] studentScoresArray = new int[numberOfStudents];

        // Prompt the user to enter the names
        System.out.print("Enter names: ");

        // Read each name and store it in the names array
        for (int currentIndex = 0; currentIndex < numberOfStudents; currentIndex++) {
            // Read the next name from the input
            String currentStudentName = userInputScanner.next();
            // Store the name in the array at the current index
            studentNamesArray[currentIndex] = currentStudentName;
        }

        // Prompt the user to enter the scores
        System.out.print("Enter scores: ");

        // Read each score and store it in the scores array
        for (int currentIndex = 0; currentIndex < numberOfStudents; currentIndex++) {
            // Read the next score from the input
            int currentStudentScore = userInputScanner.nextInt();
            // Store the score in the array at the current index
            studentScoresArray[currentIndex] = currentStudentScore;
        }

        // Sort the students by their scores in ascending order using a simple bubble sort
        // We must keep names and scores aligned (parallel arrays)
        for (int outerIndex = 0; outerIndex < numberOfStudents - 1; outerIndex++) {
            for (int innerIndex = 0; innerIndex < numberOfStudents - 1 - outerIndex; innerIndex++) {
                int currentScore = studentScoresArray[innerIndex];
                int nextScore = studentScoresArray[innerIndex + 1];

                // If the current score is greater than the next score, swap them
                if (currentScore > nextScore) {
                    // Swap scores
                    int temporaryScoreHolder = studentScoresArray[innerIndex];
                    studentScoresArray[innerIndex] = studentScoresArray[innerIndex + 1];
                    studentScoresArray[innerIndex + 1] = temporaryScoreHolder;

                    // Swap corresponding names to keep arrays in sync
                    String temporaryNameHolder = studentNamesArray[innerIndex];
                    studentNamesArray[innerIndex] = studentNamesArray[innerIndex + 1];
                    studentNamesArray[innerIndex + 1] = temporaryNameHolder;
                }
            }
        }

        // After sorting in ascending order, the highest score will be at the last index
        // We will handle the case where there might be zero students
        if (numberOfStudents != 0) {
            int indexOfTopStudent = numberOfStudents - 1;

            // Retrieve the top student's name and score from the arrays
            String topStudentName = studentNamesArray[indexOfTopStudent];
            int topStudentScore = studentScoresArray[indexOfTopStudent];

            // Print the result in the required format
            System.out.println("Top student: " + topStudentName + " (" + topStudentScore + ")");
        }

        // Close the Scanner to avoid resource leaks
        userInputScanner.close();
    }
}