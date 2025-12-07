import java.util.Scanner;

public class Q2 {

    public static void main(String[] args) {

        // Create a Scanner object to read user input from the console
        Scanner keyboardScanner = new Scanner(System.in);

        // Prompt the user to enter the number of students
        System.out.print("Enter number of students: ");
        int numberOfStudents = keyboardScanner.nextInt();

        // Edge case: if numberOfStudents is less than or equal to 0, we cannot proceed
        if (numberOfStudents <= 0) {
            // If invalid, we just do nothing or could print nothing since no students exist
            // I will close the scanner and end the program here
            keyboardScanner.close();
            return;
        }

        // Create an array to hold the student names
        String[] studentNamesArray = new String[numberOfStudents];

        // Create an array to hold the student scores
        int[] studentScoresArray = new int[numberOfStudents];

        // Prompt for names
        System.out.print("Enter names: ");
        // Read each name and store it in the names array
        for (int index = 0; index < numberOfStudents; index++) {
            // Read the next name token
            String currentNameInput = keyboardScanner.next();
            studentNamesArray[index] = currentNameInput;
        }

        // Prompt for scores
        System.out.print("Enter scores: ");
        // Read each score and store it in the scores array
        for (int index = 0; index < numberOfStudents; index++) {
            int currentScoreInput = keyboardScanner.nextInt();
            studentScoresArray[index] = currentScoreInput;
        }

        // Now we need to sort both arrays based on scores in ascending order
        // We will use a simple bubble sort to be safe and clear about what is happening
        boolean didWeSwapInThisPass = true;

        // Temporary holder variables for swapping
        String temporaryNameHolder;
        int temporaryScoreHolder;

        // Perform bubble sort while swaps are happening
        while (didWeSwapInThisPass) {

            // Assume no swaps at the beginning of this pass
            didWeSwapInThisPass = false;

            // Go through the arrays and compare adjacent elements
            for (int index = 0; index < numberOfStudents - 1; index++) {

                // Compare the scores of adjacent elements
                int currentScore = studentScoresArray[index];
                int nextScore = studentScoresArray[index + 1];

                // If current score is greater than next score, we need to swap to sort ascending
                if (currentScore > nextScore) {

                    // Swap scores
                    temporaryScoreHolder = studentScoresArray[index];
                    studentScoresArray[index] = studentScoresArray[index + 1];
                    studentScoresArray[index + 1] = temporaryScoreHolder;

                    // Swap corresponding names to keep arrays parallel
                    temporaryNameHolder = studentNamesArray[index];
                    studentNamesArray[index] = studentNamesArray[index + 1];
                    studentNamesArray[index + 1] = temporaryNameHolder;

                    // A swap happened, so set the flag to true
                    didWeSwapInThisPass = true;
                }
            }
        }

        // After sorting in ascending order, the highest score will be at the last index
        int lastIndexPosition = numberOfStudents - 1;
        if (lastIndexPosition >= 0) { // Extra safety check even though it should always be true here
            String topStudentName = studentNamesArray[lastIndexPosition];
            int topStudentScore = studentScoresArray[lastIndexPosition];

            // Print the top student in the required format
            System.out.println("Top student: " + topStudentName + " (" + topStudentScore + ")");
        }

        // Close the scanner to avoid resource leaks
        keyboardScanner.close();
    }
}